package ir.dagger.quizak.services.db

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.QuizCommand
import ir.dagger.quizak.controller.command.UserCommand
import ir.dagger.quizak.controller.command.converters.QuizCommandConverter
import ir.dagger.quizak.controller.command.converters.UserCommandConverter
import ir.dagger.quizak.controller.command.converters.UserConverter
import ir.dagger.quizak.db.entity.MediaType
import ir.dagger.quizak.db.entity.customers.EmailVerification
import ir.dagger.quizak.db.entity.customers.User
import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.repostiory.EmailVerificationRepository
import ir.dagger.quizak.db.repostiory.UserRepository
import ir.dagger.quizak.services.FileService
import ir.dagger.quizak.services.email.EmailService
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.HttpClientErrorException
import java.time.Duration
import java.time.LocalDateTime

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val quizCommandConverter: QuizCommandConverter,
    private val emailVerificationRepository: EmailVerificationRepository,
    private val userCommandConverter: UserCommandConverter,
    private val userConverter: UserConverter,
    private val emailService: EmailService,
    private val fileService: FileService,
) : UserService {

    @Transactional
    override fun createUser(userCommand: UserCommand, password: String): UserCommand {
        val user = userConverter.convert(userCommand)!!
        user.hash = passwordEncoder.encode(password)
        val returned = userRepository.save(user)

        val emailVerification =
            emailVerificationRepository.save(EmailVerification(returned.email!!, returned))

        //TODO: Refactor this code as soon as possible!!!!!!!!!
        emailService.sendSimpleMail(
            returned.email!!,
            "Please verify your email:",
            """
                Click on the link below, or copy url in your browser:
                    /verifyEmail/${emailVerification.id}
                If it wasn't you who signed up for Quizak just ignore this email.
            """.trimIndent()
        )

        return userCommandConverter.convert(returned)
    }

    @Transactional
    override fun updateUser(
        userCommand: UserCommand,
        user: ApplicationUser,
    ): User {
        val oldUser = userRepository.findById(userCommand.id!!)
        if (userCommand.id != null && userCommand.id == user.id && !oldUser.isEmpty) {
            oldUser.get().apply {
                userCommand.mediaData.file?.let {
                    if (!it.isEmpty)
                        oldUser.get().media =
                            fileService.save(it, MediaType.Picture)
                            .orElseThrow()
                }

                description = userCommand.description
                familyName = userCommand.familyName!!
                uniqueName = userCommand.uniqueName!!
                bornAt = userCommand.bornAt
                name = userCommand.name!!
            }
            return userRepository.save(oldUser.get())
        } else throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "text.error.unauthorized")
    }

    override fun myCreations(user: ApplicationUser): List<QuizCommand> =
        userRepository.findById(user.id)
            .orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND, "text.error.not_found") }
            .getCreatedBy().asSequence().sortedByDescending(Quiz::createdAt)
            .map(quizCommandConverter::convert).toList()

    override fun findUserByUniqueName(uniqueName: String): UserCommand {
        val user = userRepository.findByUniqueName(uniqueName)
            .orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND, "text.error.not_found") }
        return userCommandConverter.convert(user)
    }

    override fun findUserById(userId: String): UserCommand {
        val user = userRepository.findById(userId).orElseThrow {
            HttpClientErrorException(HttpStatus.NOT_FOUND, "text.error.not_found")
        }
        return userCommandConverter.convert(user)
    }

    @Transactional
    override fun verifyEmail(id: String) {
        val emailVerification = emailVerificationRepository.findById(id)
            .orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND, "text.error.not_found") }
        val user = userRepository.findByEmail(emailVerification.email)
            .orElseThrow { HttpClientErrorException(HttpStatus.BAD_REQUEST, "text.error.not_found") }
        val duration = Duration.between(
            emailVerification.createdAt,
            LocalDateTime.now()
        ).toHours()

        if (duration >= 24) {
            userRepository.delete(user)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "text.error.signUp.verification")
        } else {
            user.isEnabled = true
            userRepository.save(user)
        }
        emailVerificationRepository.delete(emailVerification)
    }
}