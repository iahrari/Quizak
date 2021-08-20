package ir.dagger.quizak.controller.command

import ir.dagger.quizak.db.entity.quiz.QuizType

class TrueFalseQCommand: BaseQuestionCommand() {
    var correct: Boolean = true
    init {
        type = QuizType.TrueFalse
    }
}