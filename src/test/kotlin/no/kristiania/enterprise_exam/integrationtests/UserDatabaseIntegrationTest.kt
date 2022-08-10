package no.kristiania.enterprise_exam.integrationtests

import no.kristiania.enterprise_exam.dto.NewUserInfo
import no.kristiania.enterprise_exam.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(UserService::class)
class UserDatabaseIntegrationTest(@Autowired private val userService: UserService) {

    @Test
    fun registerAndFindUser(){
        val newUserInfo = NewUserInfo("user1@gmail.com", "password1")

        val createdUser = userService.registerUser(newUserInfo)
        assert(createdUser.email == newUserInfo.email)

        val retrievedUser = userService.loadUserByUsername(newUserInfo.email)
        assert(retrievedUser.username == newUserInfo.email)
    }

    @Test
    fun shouldGetUsers() {
        val result = userService.getUsers()
        assert(result[0].email == "admin@admin.com")
    }

    @Test
    fun createUserThenGetByEmail() {
        val newUserInfo = NewUserInfo("user1@gmail.com", "password1")

        val createdUser = userService.registerUser(newUserInfo)
        assert(createdUser.email == newUserInfo.email)

        val retrievedUser = userService.getUserByEmail(newUserInfo.email)
        assert(retrievedUser.email == newUserInfo.email)
    }

}