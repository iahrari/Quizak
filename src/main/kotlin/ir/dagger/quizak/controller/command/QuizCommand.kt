package ir.dagger.quizak.controller.command

class QuizCommand: MainEntityCommand() {
    var classId: String? = null
    var private : Boolean = true
    var instituteId: String? = null
    var createdById: String? = null

}