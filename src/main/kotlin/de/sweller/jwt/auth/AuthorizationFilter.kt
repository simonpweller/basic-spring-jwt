package de.sweller.jwt.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import de.sweller.jwt.config.HEADER_NAME
import de.sweller.jwt.config.HEADER_PREFIX
import de.sweller.jwt.config.KEY
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {

    private val algorithm = Algorithm.HMAC256(KEY)
    private val verifier = JWT.require(algorithm).build()

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val token = request.getHeader(HEADER_NAME)?.replace(HEADER_PREFIX, "")
        token?.let {
            SecurityContextHolder.getContext().authentication = getAuthentication(token)
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val jwt = verifier.verify(token)
        return UsernamePasswordAuthenticationToken(jwt.subject, null, emptyList())
    }
}
