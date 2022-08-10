package no.kristiania.enterprise_exam.unittests.controller

import io.mockk.every
import io.mockk.mockk
import no.kristiania.enterprise_exam.TestData
import no.kristiania.enterprise_exam.model.DeliveryEntity
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
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get

@ExtendWith(SpringExtension::class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class DeliveryControllerUnitTest {

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
    private lateinit var deliveryService: DeliveryService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllDeliveries() {
        val delivery1 = TestData().getDeliveryTestData(0)

        every { deliveryService.getDeliveries() } answers {
            mutableListOf(delivery1)
        }

        mockMvc.get("/api/delivery/all")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()
    }

    @Test
    fun shouldGetAllDeliveryById() {
        val delivery1 = TestData().getDeliveryTestData(0)

        every { deliveryService.getDeliveryById(any()) } answers {
            delivery1
        }

        mockMvc.get("/api/delivery/1")
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()
    }

    @Test
    fun shouldDeleteOrder(){
        val delivery1 = TestData().getDeliveryTestData(0)
        every { deliveryService.deleteOrderById(any()) } answers {
            delivery1
        }

        mockMvc.delete("/api/delivery/delete/1")
            .andExpect { status { isNoContent() } }
            .andReturn()
    }


}