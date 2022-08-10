package no.kristiania.enterprise_exam.unittests.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import no.kristiania.enterprise_exam.dto.AuthorityToUser
import no.kristiania.enterprise_exam.dto.NewAuthorityInfo
import no.kristiania.enterprise_exam.dto.NewUserInfo
import no.kristiania.enterprise_exam.model.AuthorityEntity
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
class AuthorityControllerUnitTest {

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
    private lateinit var authorityService: AuthorityService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldReturnRegisteredAuthority() {
        every { authorityService.createAuthority(any()) } answers {
            AuthorityEntity(1, "SUPER")
        }

        mockMvc.post("/api/authority/create")
        {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(NewAuthorityInfo("SUPER"))
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

    @Test
    fun shouldGetAllAuthority() {
        val auth1 = AuthorityEntity(1, "SUPER")
        every { authorityService.getAuthorities() } answers {
            mutableListOf("SUPER")
        }

        mockMvc.get("/api/authority/all")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
    }

    @Test
    fun shouldAddAuthorityToUser() {
        val user1 = UserEntity(email = "user1@gmail.com", password = "password1")
        val auth1 = AuthorityEntity(1, "SUPER")

        every { authorityService.grantAuthorityToUser(any(), any()) } answers {
            user1
        }

        mockMvc.post("/api/authority/addToUser") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(AuthorityToUser("user1@gmail.com", "SUPER"))
        }
            .andExpect { status { isOk() } }

    }
}


