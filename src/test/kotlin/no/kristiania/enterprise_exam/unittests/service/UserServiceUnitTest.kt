package no.kristiania.enterprise_exam.unittests.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.enterprise_exam.dto.NewUserInfo
import no.kristiania.enterprise_exam.model.AuthorityEntity
import no.kristiania.enterprise_exam.model.UserEntity
import no.kristiania.enterprise_exam.repository.AuthorityRepo
import no.kristiania.enterprise_exam.repository.UserRepo
import no.kristiania.enterprise_exam.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.util.*

class UserServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val authorityRepo = mockk<AuthorityRepo>()
    private val userService = UserService(userRepo)

    @Test
    fun shouldRegisterNewUser() {
        every { userRepo.save(any()) } answers {
            firstArg()
        }
        every { authorityRepo.findByName(any()) } answers {
            AuthorityEntity(name = "USER")
        }

        val createdUser = userService.registerUser(NewUserInfo("newuser@gmail.com", "pirate"))
        assert(createdUser.email == "newuser@gmail.com")
    }

    @Test
    fun shouldGetAllUsers() {
        val userJohn = UserEntity(email = "john@gmail.com", password = "johnpw")
        val userVic = UserEntity(email = "victor@gmail.com", password = "vicpw")
        every { userRepo.findAll() } answers {
            mutableListOf(userJohn, userVic)
        }

        val users = userService.getUsers()
        assert(users.size == 2)
        assert(users.first { it.email == "john@gmail.com" }.password == "johnpw")
    }

    @Test
    fun shouldGetUserByEmail() {
        val userVic = UserEntity(userId = 2, email = "victor@gmail.com", password = "vicpw")
        every { userRepo.findByEmail("victor@gmail.com") } answers {
            userVic
        }

        val user = userService.getUserByEmail("victor@gmail.com")

        assert(user.email == "victor@gmail.com")
        assert(user.userId == 2L)
    }

    @Test
    fun shouldGetUserById() {
        val userJohn = UserEntity(userId = 1, email = "john@gmail.com", password = "johnpw")
        every { userRepo.findByIdOrNull(1) } answers {
            userJohn
        }

        val user = userService.getUserById(1)

        if (user != null) {
            assert(user.email == "john@gmail.com")
            assert(user.userId == 1L)
        }
    }



}