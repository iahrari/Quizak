package ir.dagger.quizak.controller.command

open class MainEntityCommand: BaseEntityCommand() {
    var name: String? = null
    var description: String? = null
    var mediaData = MediaData()
}