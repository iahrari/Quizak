package ir.dagger.quizak.db.entity.quiz.question

import ir.dagger.quizak.db.entity.base.TimeStamps
import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.entity.quiz.BaseQuestion
import ir.dagger.quizak.db.entity.quiz.QuestionId
import ir.dagger.quizak.db.entity.quiz.QuizType
import javax.persistence.Entity

@Entity
class TrueFalseQ(
    question: String,
    answerTime: Long,
    val isTrue: Boolean,
): BaseQuestion(question, answerTime, QuizType.TrueFalse)