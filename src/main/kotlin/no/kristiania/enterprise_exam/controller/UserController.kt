package no.kristiania.enterprise_exam.controller

import no.kristiania.enterprise_exam.dto.NewUserInfo
import no.kristiania.enterprise_exam.model.UserEntity
import no.kristiania.enterprise_exam.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api")
class UserController(@Autowired private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody newUser: NewUserInfo): ResponseEntity<UserEntity> {
        val user = userService.registerUser(newUser)
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register/user").toUriString())
        return ResponseEntity.created(uri).body(user)
    }

    @GetMapping("/user/all")
    fun getUsers(): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok().body(userService.getUsers())
    }

    @GetMapping("/user/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserEntity>{
        return ResponseEntity(userService.getUserById(id), HttpStatus.OK)
    }

    @GetMapping("/user/email/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<UserEntity>{
        return ResponseEntity(userService.getUserByEmail(email), HttpStatus.OK)
    }



}