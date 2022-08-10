package no.kristiania.enterprise_exam.service

import no.kristiania.enterprise_exam.dto.DeliveryDTO
import no.kristiania.enterprise_exam.dto.OrderedProductDTO
import no.kristiania.enterprise_exam.model.DeliveryEntity
import no.kristiania.enterprise_exam.model.OrderedProductEntity
import no.kristiania.enterprise_exam.model.ProductEntity
import no.kristiania.enterprise_exam.repository.CustomerRepo
import no.kristiania.enterprise_exam.repository.DeliveryRepo
import no.kristiania.enterprise_exam.repository.OrderedProductRepo
import no.kristiania.enterprise_exam.repository.ProductRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap

@Service
class DeliveryService(
    @Autowired private val deliveryRepo: DeliveryRepo,
    @Autowired private val customerRepo: CustomerRepo,
    @Autowired private val orderedProductRepo: OrderedProductRepo,
    @Autowired private val productRepo: ProductRepo
) {
    fun getDeliveries(): List<DeliveryEntity> {
        return deliveryRepo.findAll()
    }

    fun getDeliveryById(id: Long): DeliveryEntity? {
        return deliveryRepo.findByIdOrNull(id)
    }

    fun deleteOrderById(id: Long) {
        return deliveryRepo.deleteById(id)
    }

    fun updateOrderedProduct(orderedProductId: Long, orderedProductDTO: OrderedProductDTO): OrderedProductEntity? {

        return if (!orderedProductRepo.existsById(orderedProductId)) {
            null
        } else {
            val oldOrderedProduct = orderedProductRepo.findByIdOrNull(orderedProductId)

            val updatedOrderedProduct = OrderedProductEntity(
                orderedProductId = orderedProductId,
                quantity = orderedProductDTO.quantity ?: oldOrderedProduct?.quantity,
                product = ((productRepo.findByIdOrNull(orderedProductDTO.productId ?: oldOrderedProduct?.product?.id)
                    ?: oldOrderedProduct) as ProductEntity?)
            )

            return orderedProductRepo.save(updatedOrderedProduct)
        }

    }

    fun registerDelivery(deliveryDTO: DeliveryDTO): DeliveryEntity {

        val newDelivery = DeliveryEntity(
            customer = customerRepo.findByIdOrNull(deliveryDTO.customerId),
            orderedProducts = deliveryDTO.orderedProductsDTO.map { p ->
                OrderedProductEntity(
                    product = productRepo.findByIdOrNull(p.productId),
                    quantity = p.quantity,
                )

            } as MutableList<OrderedProductEntity>,
        )

        return deliveryRepo.save(newDelivery)
    }

}
