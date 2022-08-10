package no.kristiania.enterprise_exam.controller

import no.kristiania.enterprise_exam.dto.AuthorityToUser
import no.kristiania.enterprise_exam.dto.NewAuthorityInfo
import no.kristiania.enterprise_exam.model.AuthorityEntity
import no.kristiania.enterprise_exam.service.AuthorityService
import no.kristiania.enterprise_exam.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api")
class AuthorityController(
    @Autowired private val userService: UserService,
    @Autowired private val authorityService: AuthorityService
    ) {

    @PostMapping("/authority/create")
    fun registerAuthority(@RequestBody newAuthorityInfo: NewAuthorityInfo): ResponseEntity<AuthorityEntity> {
        val authorityEntity = authorityService.createAuthority(newAuthorityInfo)
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register/authority").toUriString()
        )
        return ResponseEntity.created(uri).body(authorityEntity)
    }

    @GetMapping("/authority/all")
    fun getAuthorities(): ResponseEntity<List<String?>> {
        return ResponseEntity.ok().body(authorityService.getAuthorities())
    }

    @PostMapping("/authority/addToUser")
    fun addAuthorityToUser(@RequestBody authorityToUser: AuthorityToUser): ResponseEntity<Any> {
        authorityService.grantAuthorityToUser(authorityToUser.email, authorityToUser.authority)
        return ResponseEntity.ok().build()
    }


}

