package ir.dagger.quizak.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

//TODO: Configure grantedAuthorities
class ApplicationUser(
    val id: String,
    private var username: String,
    private var password: String,
    private val isAccountNonExpired: Boolean,
    private val isAccountNonLocked: Boolean,
    private val isCredentialsNonExpired: Boolean,
    private val isEnabled: Boolean,
    private val grantedAuthorities: Set<GrantedAuthority> = hashSetOf(),
    var mediaId: String? = null,
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return grantedAuthorities.toMutableSet()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return isAccountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return isAccountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isCredentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPassword(password: String) {
        this.password = password
    }
}