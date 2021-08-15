package ir.dagger.quizak.controller.command

open class MainEntityCommand(): BaseEntityCommand() {
    var name: String? = null
    var description: String? = null

    constructor(name: String?, description: String?) : this() {
        this.name = name
        this.description = description
    }
}