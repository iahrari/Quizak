package ir.dagger.quizak.controller.command

import javax.validation.constraints.Size

open class MainEntityCommand: BaseEntityCommand() {
    @Size(min = 2, max = 255)
    var name: String? = null
    var description: String? = null
    var mediaData = MediaData()
}