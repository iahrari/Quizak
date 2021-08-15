package ir.dagger.quizak.services.db

import ir.dagger.quizak.controller.command.converters.UserCommandConverter
import ir.dagger.quizak.controller.command.converters.UserConverter
import ir.dagger.quizak.db.entity.customers.EmailVerification
import ir.dagger.quizak.db.entity.customers.User
import ir.dagger.quizak.db.repostiory.EmailVerificationRepository
import ir.dagger.quizak.db.repostiory.UserRepository
import ir.dagger.quizak.services.email.EmailService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class UserServiceImplTest {
    @Mock private lateinit var userRepository: UserRepository
    @Mock private lateinit var emailVerificationRepository: EmailVerificationRepository
    private lateinit var userCommandConverter: UserCommandConverter
    private lateinit var userConverter: UserConverter
    @Mock private lateinit var emailService: EmailService
    private lateinit var userService: UserServiceImpl

    @BeforeEach
    fun setUp() {
        userCommandConverter = UserCommandConverter()
        userConverter = UserConverter(BCryptPasswordEncoder(10))
        userService = UserServiceImpl(
            userRepository,
            emailVerificationRepository,
            userCommandConverter,
            userConverter,
            emailService
        )
    }

    @Test
    fun createUser() {
    }

    @Test
    fun findUserByUniqueName() {
    }

    @Test
    fun verifyOldEmail() {
        //given
        val user = User("", null, "", "", "", "", "")
        val emailVerification = EmailVerification("email", user).apply {
            createdAt = LocalDateTime.now().minusHours(24)
        }
        `when`(emailVerificationRepository.findById(anyString())).thenReturn(
            Optional.of(emailVerification)
        )
        `when`(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user))

        //when
        userService.verifyEmail("")

        //then
        verify(userRepository, never()).save(any())
        verify(userRepository, times(1)).delete(any())
    }

    @Test
    fun verifyNewEmail() {
        //given
        val user = User("", null, "", "", "", "", "")
        val emailVerification = EmailVerification("email", user).apply {
            createdAt = LocalDateTime.now().minusHours(23)
        }
        `when`(emailVerificationRepository.findById(anyString())).thenReturn(
            Optional.of(emailVerification)
        )
        `when`(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user))

        //when
        userService.verifyEmail("")

        //then
        verify(userRepository, times(1)).save(any())
        verify(userRepository, never()).delete(any())
    }
}