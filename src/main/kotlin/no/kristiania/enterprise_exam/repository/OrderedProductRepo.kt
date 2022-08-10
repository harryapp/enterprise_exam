package no.kristiania.enterprise_exam.repository

import no.kristiania.enterprise_exam.model.OrderedProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderedProductRepo : JpaRepository<OrderedProductEntity, Long> {
}