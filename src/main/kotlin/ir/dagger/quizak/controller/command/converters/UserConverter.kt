package ir.dagger.quizak.controller.command.converters

import ir.dagger.quizak.controller.command.UserCommand
import ir.dagger.quizak.db.entity.customers.User
import org.springframework.stereotype.Component

@Component
class UserConverter: KConverter<UserCommand, User> {
    @Synchronized
    override fun convert(source: UserCommand): User? =
        if (source.phone == null && source.email == null) null
        else User(
            familyName = source.familyName!!,
            bornAt = source.bornAt,
            verifiedTeacher = source.verifiedTeacher,
            isTeacher = source.isTeacher,
            uniqueName = source.uniqueName!!,
            phone = source.phone,
            email = source.email,
            name = source.name!!,
            description = source.description
        ).apply {
            isExpired = source.isExpired
            isEnabled = source.isEnabled
            id = source.id
        }
}

@Component
class UserCommandConverter: KConverter<User, UserCommand> {
    @Synchronized
    override fun convert(source: User): UserCommand =
        UserCommand()
            .apply {
                copyBaseCustomerCommandData(source, this)
                familyName = source.familyName
                bornAt = source.bornAt
                verifiedTeacher = source.verifiedTeacher
                isTeacher = source.isTeacher
            }
}