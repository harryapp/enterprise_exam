package no.kristiania.enterprise_exam.unittests.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.enterprise_exam.TestData
import no.kristiania.enterprise_exam.model.*
import no.kristiania.enterprise_exam.repository.CustomerRepo
import no.kristiania.enterprise_exam.repository.DeliveryRepo
import no.kristiania.enterprise_exam.repository.OrderedProductRepo
import no.kristiania.enterprise_exam.repository.ProductRepo
import no.kristiania.enterprise_exam.service.DeliveryService
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class DeliveryServiceUnitTest {

    private val deliveryRepo = mockk<DeliveryRepo>()
    private val customerRepo = mockk<CustomerRepo>()
    private val orderedProductRepo = mockk<OrderedProductRepo>()
    private val productRepo = mockk<ProductRepo>()


    private val deliveryService = DeliveryService(deliveryRepo, customerRepo, orderedProductRepo, productRepo)

    @Test
    fun shouldGetAllDeliveries() {

        every { deliveryRepo.findAll() } answers {
            mutableListOf(TestData().getDeliveryTestData(1))
        }

        val deliveries = deliveryService.getDeliveries()
        assert(deliveries.size == 1)
        assert(deliveries[0].customer?.email === "cust@gmail.com")
    }

    @Test
    fun shouldGetDeliveryById(){

        every { deliveryRepo.findByIdOrNull(any()) } answers {
            TestData().getDeliveryTestData(1)
        }

        val delivery = deliveryService.getDeliveryById(1)
        assert(delivery?.customer?.email === "cust@gmail.com")
        assert(delivery?.orderId == 1L)
    }

    @Test
    fun shouldUpdateOrderedProduct(){

        every { orderedProductRepo.existsById(any()) } answers {
            firstArg()
        }

        every { orderedProductRepo.findByIdOrNull(any()) } answers {
            firstArg()
        }

        every { deliveryRepo.save(any()) } answers {
            firstArg()
        }


    }

}