package no.kristiania.enterprise_exam.integrationtests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.kristiania.enterprise_exam.TestData
import no.kristiania.enterprise_exam.dto.NewUserInfo
import no.kristiania.enterprise_exam.dto.OrderedProductDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*
import java.net.HttpCookie

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FullSystemTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    fun getAdminLoginData(): String {
        return """{
            "username": "admin@admin.com",
            "password": "pirate"
        }"""
    }

    fun getUserLoginData(): String {
        return """{
            "username": "user@user.com",
            "password": "pirate"
        }"""
    }

    @Test
    fun getGetAllAuthoritiesIntegrationTest() {
        val loggedInUser = mockMvc.post("/api/login") {
            contentType = APPLICATION_JSON
            content = getAdminLoginData()
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val theCookie = loggedInUser.response.getCookie("access_token")
        mockMvc.get("/api/user/all") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andExpect { jsonPath("$") { isArray() } }
            .andReturn()
    }

    @Test
    fun getGetAllDeliveriesIntegrationTest() {
        val loggedInUser = mockMvc.post("/api/login") {
            contentType = APPLICATION_JSON
            content = getAdminLoginData()
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val theCookie = loggedInUser.response.getCookie("access_token")
        mockMvc.get("/api/delivery/all") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andExpect { jsonPath("$") { isArray() } }
            .andReturn()
    }

    @Test
    fun shouldGetDeliveryById() {
        val loggedInUser = mockMvc.post("/api/login") {
            contentType = APPLICATION_JSON
            content = getAdminLoginData()
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val theCookie = loggedInUser.response.getCookie("access_token")
        mockMvc.get("/api/delivery/1") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andReturn()
    }

    @Test
    fun shouldBeAbleToRegisterNewUser() {


        mockMvc.post("/api/register") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(NewUserInfo("test@test.no", "1234"))

        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andReturn()
    }

    @Test
    fun shouldBeAbleToRegisterNewDelivery() {
        val loggedInUser = mockMvc.post("/api/login") {
            contentType = APPLICATION_JSON
            content = getAdminLoginData()
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val theCookie = loggedInUser.response.getCookie("access_token")
        mockMvc.post("/api/delivery/new") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(TestData().getDeliveryDTOTestData())
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andReturn()
    }

    @Test
    fun shouldBeAbleToUpdateOrderedProduct() {
        val loggedInUser = mockMvc.post("/api/login") {
            contentType = APPLICATION_JSON
            content = getAdminLoginData()
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val theCookie = loggedInUser.response.getCookie("access_token")
        mockMvc.put("/api/delivery/update/orderedProduct/1") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(OrderedProductDTO(1, 80))
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andReturn()
    }

    @Test
    fun shouldDeleteDelivery() {
        val loggedInUser = mockMvc.post("/api/login") {
            contentType = APPLICATION_JSON
            content = getAdminLoginData()
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val theCookie = loggedInUser.response.getCookie("access_token")
        mockMvc.delete("/api/delivery/delete/2") {
            theCookie?.let { cookie(it) }
        }
            .andExpect { status { isNoContent() } }
            .andReturn()
    }


@Test
fun notRegUserShouldNotGetAccess() {

    mockMvc.get("/api/user") {
    }
        .andExpect { status { isForbidden() } }
        .andReturn()

    mockMvc.get("/api/delivery") {
    }
        .andExpect { status { isForbidden() } }
        .andReturn()

    mockMvc.get("/api/authority") {
    }
        .andExpect { status { isForbidden() } }
        .andReturn()
}

}




