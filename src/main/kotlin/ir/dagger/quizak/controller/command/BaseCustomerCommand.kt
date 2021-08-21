package ir.dagger.quizak.controller.command

open class BaseCustomerCommand: MainEntityCommand() {
    var uniqueName: String? = null
    var password: String? = null
    var phone: String? = null
    var email: String? = null
    var isEnabled: Boolean = false
    var isExpired: Boolean = false
}