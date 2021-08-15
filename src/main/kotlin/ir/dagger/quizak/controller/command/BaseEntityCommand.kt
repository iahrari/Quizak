package ir.dagger.quizak.controller.command

open class BaseEntityCommand() {
    var id: String? = null
    constructor(id: String?): this(){
        this.id = id
    }
}