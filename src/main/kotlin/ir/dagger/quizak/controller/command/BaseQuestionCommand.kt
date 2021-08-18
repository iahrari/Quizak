package ir.dagger.quizak.controller.command

import ir.dagger.quizak.db.entity.quiz.QuizType
import org.springframework.web.multipart.MultipartFile

open class BaseQuestionCommand {
    var quizId: String? = null
    var rowId: Int? = null
    var mediaId: String? = null
    var multipartFile: MultipartFile? = null
    var type: QuizType? = null

    var question: String? = null

    /** answerTime is in seconds */
    var answerTime: Long = 14
}