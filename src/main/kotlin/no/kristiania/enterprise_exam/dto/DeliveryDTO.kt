package no.kristiania.enterprise_exam.dto

data class NewAuthorityInfo(val name: String)

data class DeliveryDTO(val customerId: Long, val orderedProductsDTO: List<OrderedProductDTO>)

data class OrderedProductDTO(val productId: Long? = null, val quantity: Int? = null)