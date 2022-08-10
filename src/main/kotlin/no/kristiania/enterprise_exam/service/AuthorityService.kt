package no.kristiania.enterprise_exam.service

import no.kristiania.enterprise_exam.dto.NewAuthorityInfo
import no.kristiania.enterprise_exam.model.AuthorityEntity
import no.kristiania.enterprise_exam.model.UserEntity
import no.kristiania.enterprise_exam.repository.AuthorityRepo
import no.kristiania.enterprise_exam.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthorityService(
    @Autowired private val authorityRepo: AuthorityRepo,
    @Autowired private val userRepo: UserRepo
) {

    fun createAuthority(newAuthorityInfo: NewAuthorityInfo): AuthorityEntity {
        return authorityRepo.save(AuthorityEntity(name = newAuthorityInfo.name))
    }

    fun grantAuthorityToUser(email: String?, authorityName: String?): UserEntity {
        val user = userRepo.findByEmail(email)
        val authority = authorityRepo.findByName(authorityName)
        user.authorities.add(authority)
        return userRepo.save(user)
    }

    fun getAuthorities(): List<String?>{
        return authorityRepo.findAll().map { it.name }
    }
}