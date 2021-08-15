package ir.dagger.quizak.db.entity.quiz.question

import ir.dagger.quizak.db.entity.base.TimeStamps
import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.entity.quiz.BaseQuestion
import ir.dagger.quizak.db.entity.quiz.QuestionId
import ir.dagger.quizak.db.entity.quiz.QuizType
import javax.persistence.Entity

@Entity
class MultiChoiceQ(
    question: String,
    answerTime: Long,

    val opt1: String,
    val opt2: String,
    val opt3: String?,
    val opt4: String?,

    val isAnswer1: Boolean,
    val isAnswer2: Boolean,
    val isAnswer3: Boolean?,
    val isAnswer4: Boolean?,
) : BaseQuestion(question, answerTime, QuizType.MultiChoice) {
}