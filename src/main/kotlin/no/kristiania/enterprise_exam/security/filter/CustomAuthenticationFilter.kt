package no.kristiania.enterprise_exam.security.filter

import no.kristiania.enterprise_exam.security.jwt.JwtUtil
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.kristiania.enterprise_exam.dto.UserLoginInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



class CustomAuthenticationFilter(
    @Autowired private val authManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val body = request.reader.lines().collect(Collectors.joining())
        val loginInfo = jacksonObjectMapper().readValue(body, UserLoginInfo::class.java)
        val authenticationToken = UsernamePasswordAuthenticationToken(loginInfo.username, loginInfo.password)
        return authManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authentication: Authentication?
    ) {
        val user: User = authentication?.principal as User
        val accessToken = JwtUtil.createToken(user, request?.requestURL.toString())
        response?.contentType = APPLICATION_JSON_VALUE
        response?.addCookie(Cookie("access_token", accessToken))
    }
}