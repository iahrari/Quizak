package ir.dagger.quizak.controller.command

import javax.validation.constraints.Email

open class BaseCustomerCommand: BaseUniqueNameCommand() {

    //TODO: Add validation for here
    var phone: String? = null
    @Email
    var email: String? = null
    var isEnabled: Boolean = false
    var isExpired: Boolean = false
}