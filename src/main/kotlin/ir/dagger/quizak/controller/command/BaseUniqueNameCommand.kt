package ir.dagger.quizak.controller.command

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Pattern

open class BaseUniqueNameCommand: MainEntityCommand() {
    //Todo: Add pattern here
    @Length(min = 3, max = 36)
    @Pattern(regexp = "^[a-zA-Z0-9_].{2,35}$")
    var uniqueName: String? = null
}