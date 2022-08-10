package no.kristiania.enterprise_exam.repository

import no.kristiania.enterprise_exam.model.DeliveryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryRepo : JpaRepository<DeliveryEntity, Long> {

}
