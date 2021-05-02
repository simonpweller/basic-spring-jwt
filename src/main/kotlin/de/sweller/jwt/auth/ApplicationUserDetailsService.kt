package de.sweller.jwt.auth

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ApplicationUserDetailsService(
    val applicationUserRepository: ApplicationUserRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): User {
        val applicationUser: ApplicationUser = applicationUserRepository.findByUsername(username)
            ?: throw UsernameNotFoundException(username)
        return User(applicationUser.username, applicationUser.password, emptyList())
    }

}
