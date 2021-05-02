package de.sweller.jwt

import de.sweller.jwt.auth.ApplicationUser
import de.sweller.jwt.auth.ApplicationUserRepository
import de.sweller.jwt.config.SIGN_UP_URL
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    val applicationUserRepository: ApplicationUserRepository,
    val passwordEncoder: PasswordEncoder,
) {
    @PostMapping(SIGN_UP_URL)
    fun signUp(@RequestBody applicationUser: ApplicationUser) {
        applicationUser.password = passwordEncoder.encode(applicationUser.password)
        applicationUserRepository.save(applicationUser)
    }
}
