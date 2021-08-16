package ir.dagger.quizak.services.auth

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.db.repostiory.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ApplicationUserService(
    private val userRepository: UserRepository
): UserDetailsService {
    //TODO: Add support for institutes and teachers
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUniqueName(username)
            .orElseThrow{ UsernameNotFoundException("Username: $username not found!") }

        return ApplicationUser(
            id = user.id!!,
            username = user.uniqueName,
            password = user.hash,
            isAccountNonExpired = !user.isExpired,
            isAccountNonLocked = !user.isLocked,
            isCredentialsNonExpired = !user.isExpired,
            isEnabled = user.isEnabled,
            mediaId = user.media?.id
        )

    }
}