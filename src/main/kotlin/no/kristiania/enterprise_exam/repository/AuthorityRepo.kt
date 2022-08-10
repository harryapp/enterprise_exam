package no.kristiania.enterprise_exam.repository

import no.kristiania.enterprise_exam.model.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepo: JpaRepository<AuthorityEntity, Long> {

    fun findByName(authority: String?): AuthorityEntity
}
