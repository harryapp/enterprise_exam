package no.kristiania.enterprise_exam.unittests.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import io.mockk.every
import io.mockk.mockk
import no.kristiania.enterprise_exam.dto.NewUserInfo
import no.kristiania.enterprise_exam.model.UserEntity
import no.kristiania.enterprise_exam.service.AuthorityService
import no.kristiania.enterprise_exam.service.DeliveryService
import no.kristiania.enterprise_exam.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun userService() = mockk<UserService>()

        @Bean
        fun deliveryService() = mockk<DeliveryService>()

        @Bean
        fun authorityService() = mockk<AuthorityService>()
    }

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Test
    fun shouldGetAllUser() {
        val user1 = UserEntity(email = "user1@gmail.com", password = "password1")
        every { userService.getUsers() } answers {
            mutableListOf(user1)
        }

        mockMvc.get("/api/user/all")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$.[0].email") { value("user1@gmail.com") } }
            .andExpect { jsonPath("$.[0].password") { value("password1") } }
            .andReturn()
    }

    @Test
    fun shouldGetUserById() {
        val user1 = UserEntity(email = "user1@gmail.com", password = "password1")
        every { userService.getUserById(any()) } answers {
            user1
        }

        mockMvc.get("/api/user/1")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$.email") { value("user1@gmail.com") } }
            .andExpect { jsonPath("$.password") { value("password1") } }
            .andReturn()
    }

    @Test
    fun shouldGetUserByEmail() {
        val user1 = UserEntity(email = "user1@gmail.com", password = "password1")
        every { userService.getUserByEmail(any()) } answers {
            user1
        }

        mockMvc.get("/api/user/email/1")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$.email") { value("user1@gmail.com") } }
            .andExpect { jsonPath("$.password") { value("password1") } }
            .andReturn()
    }

    @Test
    fun shouldReturnRegisteredUser() {
        val user1 = UserEntity(email = "user1@gmail.com", password = "password1")
        every { userService.registerUser(any()) } answers {
            user1
        }

        mockMvc.post("/api/register")
        {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(NewUserInfo("user1@gmail.co3m", "password1"))
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect { jsonPath("$.email") { value("user1@gmail.com") } }
            .andExpect { jsonPath("$.password") { value("password1") } }
            .andReturn()
    }
}

