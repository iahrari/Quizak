package ir.dagger.quizak.services.db

import ir.dagger.quizak.controller.command.UserCommand

interface UserService {
    fun createUser(userCommand: UserCommand): UserCommand
    fun findUserByUniqueName(uniqueName: String): UserCommand
    fun verifyEmail(id: String)
}