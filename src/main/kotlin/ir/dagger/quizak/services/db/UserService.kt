package ir.dagger.quizak.services.db

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.UserCommand
import ir.dagger.quizak.db.entity.customers.User

interface UserService {
    fun createUser(userCommand: UserCommand): UserCommand
    fun findUserByUniqueName(uniqueName: String): UserCommand
    fun findUserById(userId: String): UserCommand
    fun verifyEmail(id: String)
    fun updateUser(
        userCommand: UserCommand,
        user: ApplicationUser
    ): User
}