package ir.dagger.quizak.db.entity.quiz

import ir.dagger.quizak.db.entity.quiz.answer.MultiChoiceA
import ir.dagger.quizak.db.entity.quiz.answer.TrueFalseA
import ir.dagger.quizak.db.entity.quiz.question.MultiChoiceQ
import ir.dagger.quizak.db.entity.quiz.question.TrueFalseQ

enum class QuizType(
    val questionType: Class<out BaseQuestion>,
    val answerType: Class<out BaseAnswer>,
    val text: String
) {
    MultiChoice(MultiChoiceQ::class.java , MultiChoiceA::class.java, "text.multiChoice"),
    TrueFalse(TrueFalseQ::class.java, TrueFalseA::class.java, "text.trueFalse")
}