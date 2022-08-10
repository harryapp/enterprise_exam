package no.kristiania.enterprise_exam.unittests.service

import io.mockk.every
import io.mockk.mockk
import no.kristiania.enterprise_exam.dto.NewAuthorityInfo
import no.kristiania.enterprise_exam.model.AuthorityEntity
import no.kristiania.enterprise_exam.model.UserEntity
import no.kristiania.enterprise_exam.repository.AuthorityRepo
import no.kristiania.enterprise_exam.repository.UserRepo
import no.kristiania.enterprise_exam.service.AuthorityService
import org.junit.jupiter.api.Test

class AuthorityServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val authorityRepo = mockk<AuthorityRepo>()
    private val authorityService = AuthorityService(authorityRepo, userRepo)

    @Test
    fun shouldCreateAuthority() {
        val newAuthorityInfo = NewAuthorityInfo("SUPERADMIN")

        every { authorityRepo.save(any()) } answers {
            AuthorityEntity(1, "SUPERADMIN")
        }

        every { authorityRepo.findByName("SUPERADMIN") } answers {
            AuthorityEntity(1, "SUPERADMIN")
        }

        val createdAuthority = authorityService.createAuthority(newAuthorityInfo)
        assert(createdAuthority.name === "SUPERADMIN")
    }

    @Test
    fun shouldGrantAuthorityToUser() {
        val userJohn = UserEntity(email = "john@gmail.com", password = "johnpw")

        every { userRepo.findByEmail(any()) } answers {
            userJohn
        }

        every { authorityRepo.findByName(any()) } answers {
            AuthorityEntity(name = "SUPER")
        }

        every { userRepo.save(any()) } answers {
            firstArg()
        }

        val superJohn = authorityService.grantAuthorityToUser("john@gmail.com", "SUPER")
        assert(superJohn.authorities[0].name == "SUPER")
    }

    @Test
    fun shouldGetAuthorities(){
        val auth1 = AuthorityEntity(name = "SUPER")
        val auth2 = AuthorityEntity(name = "SUPERDUPER")

        every { authorityRepo.findAll() } answers {
           mutableListOf(auth1, auth2)
        }

        val res = authorityService.getAuthorities()
        assert(res.size == 2)
        assert(res[1] == "SUPERDUPER")
    }




}