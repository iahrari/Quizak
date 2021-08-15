package ir.dagger.quizak.db.entity.quiz.answer

import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.entity.quiz.QuizSession
import ir.dagger.quizak.db.entity.customers.User
import ir.dagger.quizak.db.entity.quiz.AnswerId
import ir.dagger.quizak.db.entity.quiz.BaseAnswer
import ir.dagger.quizak.db.entity.quiz.QuizType
import java.time.LocalDateTime
import javax.persistence.Entity

@Entity
class MultiChoiceA(
    row: Int,
    startTime: LocalDateTime,
    endTime: LocalDateTime,

    val isAnswer1: Boolean,
    val isAnswer2: Boolean,
    val isAnswer3: Boolean?,
    val isAnswer4: Boolean?,
) : BaseAnswer(row, QuizType.MultiChoice, startTime, endTime) {
}