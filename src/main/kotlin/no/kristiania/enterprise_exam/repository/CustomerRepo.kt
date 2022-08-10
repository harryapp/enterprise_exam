package no.kristiania.enterprise_exam.repository

import no.kristiania.enterprise_exam.model.CustomerEntity

import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepo : JpaRepository<CustomerEntity, Long> {

}