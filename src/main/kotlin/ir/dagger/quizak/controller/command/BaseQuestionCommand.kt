package ir.dagger.quizak.controller.command

import ir.dagger.quizak.db.entity.quiz.QuizType
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

open class BaseQuestionCommand {
    var quizId: String? = null
    var rowId: Int? = null
    var mediaData = MediaData()
    var type: QuizType? = null

    @NotBlank
    var question: String? = null

    /** answerTime is in seconds */
    @Min(14)
    var answerTime: Long = 14
}