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
class TrueFalseA(
    row: Int,
    startTime: LocalDateTime,
    endTime: LocalDateTime,

    val isTrue: Boolean,
): BaseAnswer(row, QuizType.TrueFalse, startTime, endTime)