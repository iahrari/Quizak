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

    var opt1: String,
    var opt2: String,
    var opt3: String?,
    var opt4: String?,

    var isAnswer1: Boolean,
    var isAnswer2: Boolean,
    var isAnswer3: Boolean?,
    var isAnswer4: Boolean?,
) : BaseQuestion(question, answerTime, QuizType.MultiChoice) {
}