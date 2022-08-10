package no.kristiania.enterprise_exam.service

import no.kristiania.enterprise_exam.dto.NewUserInfo
import no.kristiania.enterprise_exam.model.AuthorityEntity
import no.kristiania.enterprise_exam.model.UserEntity
import no.kristiania.enterprise_exam.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired private val userRepo: UserRepo,
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userRepo.findByEmail(it)
            return User(user.email, user.password, user.authorities.map { authority -> SimpleGrantedAuthority(authority.name) })
        }
        throw UsernameNotFoundException("Error authenticating user")
    }

    fun registerUser(user: NewUserInfo): UserEntity {
        val newUser = UserEntity(email = user.email, password = BCryptPasswordEncoder().encode(user.password))
        newUser.authorities.add(AuthorityEntity(1, "USER"))
        return userRepo.save(newUser)
    }

    fun getUsers(): List<UserEntity>{
        return userRepo.findAll()
    }

    fun getUserByEmail(email: String): UserEntity {
        return userRepo.findByEmail(email)
    }

    fun getUserById(id: Long): UserEntity? {
        return userRepo.findByIdOrNull(id)
    }
}

