package no.kristiania.enterprise_exam.repository

import no.kristiania.enterprise_exam.model.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepo: JpaRepository<ProductEntity, Long> {
}