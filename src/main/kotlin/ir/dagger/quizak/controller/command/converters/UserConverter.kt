package ir.dagger.quizak.controller.command.converters

import ir.dagger.quizak.controller.command.UserCommand
import ir.dagger.quizak.db.entity.customers.User
import org.springframework.core.convert.converter.Converter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserConverter(
    private val passwordEncoder: PasswordEncoder,
) : Converter<UserCommand, User> {
    @Synchronized
    override fun convert(source: UserCommand): User? =
        if (source.phone == null && source.email == null) null
        else User(
            familyName = source.familyName!!,
            bornAt = source.bornAt,
            verifiedTeacher = source.verifiedTeacher,
            isTeacher = source.isTeacher,
            uniqueName = source.uniqueName!!,
            hash = passwordEncoder.encode(source.password),
            phone = source.phone,
            email = source.email,
            name = source.name!!,
        ).apply {
            isExpired = source.isExpired
            isEnabled = source.isEnabled
        }
}

@Component
class UserCommandConverter: Converter<User, UserCommand> {
    @Synchronized
    override fun convert(source: User): UserCommand =
        UserCommand()
            .apply {
                id = source.id
                familyName = source.familyName
                name = source.name
                description = source.description
                bornAt = source.bornAt
                verifiedTeacher = source.verifiedTeacher
                isTeacher = source.isTeacher
                uniqueName = source.uniqueName
                phone = source.phone
                email = source.email
                isEnabled = source.isEnabled
                isExpired = source.isExpired

            }
}