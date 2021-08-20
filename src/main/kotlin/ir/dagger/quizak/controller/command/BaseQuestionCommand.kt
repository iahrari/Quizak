package ir.dagger.quizak.controller.command

import ir.dagger.quizak.db.entity.quiz.QuizType

open class BaseQuestionCommand {
    var quizId: String? = null
    var rowId: Int? = null
    var mediaData = MediaData()
    var type: QuizType? = null

    var question: String? = null

    /** answerTime is in seconds */
    var answerTime: Long = 14
}