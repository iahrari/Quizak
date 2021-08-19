package ir.dagger.quizak.db.entity.quiz

import ir.dagger.quizak.controller.command.converters.KConverter
import ir.dagger.quizak.controller.command.BaseQuestionCommand
import ir.dagger.quizak.controller.command.MultiChoiceQCommand
import ir.dagger.quizak.controller.command.TrueFalseQCommand
import ir.dagger.quizak.controller.command.converters.*
import ir.dagger.quizak.db.entity.quiz.answer.MultiChoiceA
import ir.dagger.quizak.db.entity.quiz.answer.TrueFalseA
import ir.dagger.quizak.db.entity.quiz.question.MultiChoiceQ
import ir.dagger.quizak.db.entity.quiz.question.TrueFalseQ
import kotlin.reflect.KClass

enum class QuizType(
    val questionType: KClass<out BaseQuestion>,
    val answerType: KClass<out BaseAnswer>,
    val questionCommandType: KClass<out BaseQuestionCommand>,
    val converter: KClass<out KConverter<BaseQuestionCommand, BaseQuestion>>,
    val commandConverter: KClass<out KConverter<BaseQuestion, BaseQuestionCommand>>,
    val text: String
) {
    MultiChoice(
        MultiChoiceQ::class ,
        MultiChoiceA::class,
        MultiChoiceQCommand::class,
        MultiChoiceQConverter::class,
        MultiChoiceQCommandConverter::class,
        "text.multiChoice"),
    TrueFalse(
        TrueFalseQ::class,
        TrueFalseA::class,
        TrueFalseQCommand::class,
        TrueFalseQConverter::class,
        TrueFalseQCommandConverter::class,
        "text.trueFalse")
}