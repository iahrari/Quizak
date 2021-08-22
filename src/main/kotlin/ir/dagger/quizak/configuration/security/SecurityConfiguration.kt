package ir.dagger.quizak.configuration.security

import ir.dagger.quizak.services.auth.ApplicationUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val passwordEncoder: PasswordEncoder,
    private val applicationUserService: ApplicationUserService
): WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http!!.csrf().disable()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .authorizeRequests()
            .antMatchers(
                "/", "/index", "/index.html", "/css/**", "/verifyEmail/**",
                "/js/**", "/webjars/**", "/signup", "/images/**"
            ).permitAll()
            .and()
            .authorizeRequests()
                .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/")
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .rememberMe()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider =
        DaoAuthenticationProvider().apply {
            setPasswordEncoder(passwordEncoder)
            setUserDetailsService(applicationUserService)
        }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Autowired
    override fun setApplicationContext(context: ApplicationContext) {
        super.setApplicationContext(context)
        val globalAuthBuilder = context.getBean(AuthenticationManagerBuilder::class.java)
        globalAuthBuilder.userDetailsService(applicationUserService)
    }
}