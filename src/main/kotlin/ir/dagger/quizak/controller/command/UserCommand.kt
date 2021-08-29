package ir.dagger.quizak.controller.command

import java.time.LocalDate
import javax.validation.constraints.Past
import javax.validation.constraints.Size

open class UserCommand: BaseCustomerCommand(){

    @Size(min = 2, max = 255)
    var familyName: String? = null

    @Past
    var bornAt: LocalDate? = null
    var verifiedTeacher: Boolean = false
    var isTeacher = false
}