package ir.dagger.quizak.controller.command

class QuizCommand: MainEntityCommand() {
    var classId: ClassCommand? = null
    var private : Boolean = true
    var instituteId: String? = null
    var createdBy: UserCommand? = null

    var questions: List<BaseQuestionCommand> = listOf()
}