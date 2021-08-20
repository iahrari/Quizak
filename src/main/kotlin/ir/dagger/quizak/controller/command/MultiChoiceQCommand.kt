package ir.dagger.quizak.controller.command

import ir.dagger.quizak.db.entity.quiz.QuizType

class MultiChoiceQCommand: BaseQuestionCommand() {
    init {
        type = QuizType.MultiChoice
    }
    var opt1: String? = null
    var opt2: String? = null
    var opt3: String? = null
    var opt4: String? = null

    var isAnswer1: Boolean = true
    var isAnswer2: Boolean = true
    var isAnswer3: Boolean? = null
    var isAnswer4: Boolean? = null
}