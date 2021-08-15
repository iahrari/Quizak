package ir.dagger.quizak.controller.command

import java.time.LocalDate


class UserCommand(): BaseCustomerCommand(){
    var familyName: String? = null
    var bornAt: LocalDate? = null
    var verifiedTeacher: Boolean = false
    var isTeacher = false

}