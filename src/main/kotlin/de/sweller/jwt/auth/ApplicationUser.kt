package de.sweller.jwt.auth

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

interface ApplicationUserRepository: CrudRepository<ApplicationUser, Long> {
    fun findByUsername(username: String): ApplicationUser?
}

@Entity
class ApplicationUser (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var username: String,
    var password: String,
)
