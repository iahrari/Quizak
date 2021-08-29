package ir.dagger.quizak.controller.command

import javax.validation.constraints.Pattern

class AuthCommand: UserCommand() {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!|()_/]).{8,200}$")
    var password: String? = null
}