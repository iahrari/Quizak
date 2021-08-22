package ir.dagger.quizak.controller.command

open class BaseUniqueNameCommand: MainEntityCommand() {
    var uniqueName: String? = null
}