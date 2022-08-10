package no.kristiania.enterprise_exam.integrationtests

import no.kristiania.enterprise_exam.TestData
import no.kristiania.enterprise_exam.dto.OrderedProductDTO
import no.kristiania.enterprise_exam.service.DeliveryService
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
@Import(DeliveryService::class)
class DeliveryDatabaseIntegrationTest(@Autowired private val deliveryService: DeliveryService) {


    @Test
    fun shouldUpdateOrder() {
        val deliveryBeforeUpdate = deliveryService.getDeliveryById(1)

        val newQuantity = OrderedProductDTO(quantity = 5)
        val updatedOrderedProduct= deliveryService.updateOrderedProduct(1, newQuantity)
        assert(updatedOrderedProduct?.quantity == 5)

        val updatedDelivery = deliveryService.getDeliveryById(1)
        assert(updatedDelivery?.orderedProducts?.get(0)?.quantity == 5)

    }

    @Test
    fun shouldRegisterAndFindDelivery() {
        val newDeliveryInfo = TestData().getDeliveryDTOTestData()

        val createdDelivery = deliveryService.registerDelivery(newDeliveryInfo)
        assert(createdDelivery.customer?.id == newDeliveryInfo.customerId)
        assert(createdDelivery.orderedProducts[0].quantity === newDeliveryInfo.orderedProductsDTO[0].quantity)

        val retrievedDelivery = deliveryService.getDeliveryById(createdDelivery.orderId!!)


            assert(retrievedDelivery?.customer?.id == newDeliveryInfo.customerId)
            assert(retrievedDelivery?.orderedProducts?.get(0)?.quantity === newDeliveryInfo.orderedProductsDTO[0].quantity)

    }



}