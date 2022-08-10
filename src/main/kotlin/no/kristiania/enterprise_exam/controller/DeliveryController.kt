package no.kristiania.enterprise_exam.controller

import no.kristiania.enterprise_exam.dto.DeliveryDTO
import no.kristiania.enterprise_exam.dto.OrderedProductDTO
import no.kristiania.enterprise_exam.model.DeliveryEntity
import no.kristiania.enterprise_exam.service.DeliveryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/delivery")
class DeliveryController(@Autowired private val deliveryService: DeliveryService) {

    @GetMapping("/all")
    fun getDeliveries(): ResponseEntity<List<DeliveryEntity>> {
        return ResponseEntity(deliveryService.getDeliveries(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getDeliveries(@PathVariable id: Long): ResponseEntity<DeliveryEntity> {
        return ResponseEntity(deliveryService.getDeliveryById(id), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun addNewDelivery(@RequestBody deliveryDTO: DeliveryDTO): ResponseEntity<DeliveryEntity> {
        return ResponseEntity(deliveryService.registerDelivery(deliveryDTO), HttpStatus.CREATED)
    }

    @PutMapping("/update/orderedProduct/{orderedProductId}")
    fun updateOrderedProduct(
        @PathVariable orderedProductId: Long,
        @RequestBody orderedProductDTO: OrderedProductDTO
    ): ResponseEntity<Any>? {

        val updatedOrderedProduct = deliveryService.updateOrderedProduct(orderedProductId, orderedProductDTO)
        return if (updatedOrderedProduct != null) {
            ResponseEntity(updatedOrderedProduct, HttpStatus.OK)
        } else {
            ResponseEntity("not found", HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteOrder(@PathVariable id: Long): ResponseEntity<DeliveryEntity> {
        deliveryService.deleteOrderById(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}


