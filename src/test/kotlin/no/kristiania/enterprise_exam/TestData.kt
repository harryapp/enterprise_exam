package no.kristiania.enterprise_exam

import no.kristiania.enterprise_exam.dto.DeliveryDTO
import no.kristiania.enterprise_exam.dto.OrderedProductDTO
import no.kristiania.enterprise_exam.model.*

class TestData {

    fun getAddressTestData(index: Int): AddressEntity {
        return mutableListOf(
            AddressEntity(1, "street 1", "2020", "Oslo", "Norway"),
            AddressEntity(1, "street 1", "2020", "Oslo", "Norway")
        )[index]

    }

    fun getProductTestData(index: Int): ProductEntity {
        return mutableListOf(
            ProductEntity(1, "milk"),
            ProductEntity(2, "apple")
        )[index]
    }

    fun getOrderedProductData(index: Int): OrderedProductEntity {
        return mutableListOf(
            OrderedProductEntity(1, getProductTestData(0), 10),
            OrderedProductEntity(2, getProductTestData(1), 20),
            OrderedProductEntity(1, getProductTestData(0), 30),
            OrderedProductEntity(2, getProductTestData(1), 40)
        )[index]
    }

    fun getCustomerTestData(index: Int): CustomerEntity {
        return mutableListOf(
            CustomerEntity(1, "cust@gmail.com", "90909090", getAddressTestData(0)),
            CustomerEntity(1, "cust@gmail.com", "90909090", getAddressTestData(1))
        )[index]
    }

    fun getDeliveryTestData(index: Int): DeliveryEntity {
        val orderedProducts1 = mutableListOf(getOrderedProductData(0), getOrderedProductData(1))
        val delivery1 = DeliveryEntity(orderId = 1, customer = getCustomerTestData(0), orderedProducts = orderedProducts1)

        val orderedProducts2 = mutableListOf(getOrderedProductData(2), getOrderedProductData(3))
        val delivery2 = DeliveryEntity(orderId = 1, customer = getCustomerTestData(1), orderedProducts = orderedProducts2)

        val list = mutableListOf(delivery1, delivery2)
        return list[index]
    }

    fun getDeliveryDTOTestData(): DeliveryDTO{
        return DeliveryDTO(
            1,
            listOf(
                OrderedProductDTO(1, 80),
                OrderedProductDTO(2, 90)
            )
        )
    }


}