package ir.dagger.quizak.controller.command.converters

import ir.dagger.quizak.controller.command.BaseQuestionCommand
import ir.dagger.quizak.controller.command.MultiChoiceQCommand
import ir.dagger.quizak.controller.command.TrueFalseQCommand
import ir.dagger.quizak.db.entity.quiz.BaseQuestion
import ir.dagger.quizak.db.entity.quiz.question.MultiChoiceQ
import ir.dagger.quizak.db.entity.quiz.question.TrueFalseQ
import org.springframework.stereotype.Component

fun copyBaseQuestionToCommand(question: BaseQuestionCommand, source: BaseQuestion): BaseQuestionCommand{
    return question.apply {
        rowId = source.id.row
        quizId = source.id.quizId
        mediaData.mediaId = source.media?.id
        type = source.type
        this.question = source.question
        answerTime = source.answerTime / 1000
    }
}

@Component
class BaseQuestionConverter : KConverter<BaseQuestion, BaseQuestionCommand>{
    @Synchronized
    override fun convert(source: BaseQuestion): BaseQuestionCommand =
        copyBaseQuestionToCommand(BaseQuestionCommand(), source)

}

@Component
class TrueFalseQCommandConverter: KConverter<TrueFalseQ, TrueFalseQCommand> {
    @Synchronized
    override fun convert(source: TrueFalseQ): TrueFalseQCommand =
        TrueFalseQCommand().apply {
            copyBaseQuestionToCommand(this, source)
            correct = source.isTrue
        }
}

@Component
class TrueFalseQConverter: KConverter<TrueFalseQCommand, TrueFalseQ> {
    @Synchronized
    override fun convert(source: TrueFalseQCommand): TrueFalseQ =
        TrueFalseQ(
            question = source.question!!,
            answerTime = source.answerTime * 1000,
            isTrue = source.correct
        ).apply {
            id.row = source.rowId
            id.quizId = source.quizId
        }
}

@Component
class MultiChoiceQConverter: KConverter<MultiChoiceQCommand, MultiChoiceQ> {
    @Synchronized
    override fun convert(source: MultiChoiceQCommand): MultiChoiceQ =
        MultiChoiceQ(
            question = source.question!!,
            answerTime = source.answerTime * 1000,
            opt1 = source.opt1!!,
            opt2 = source.opt2!!,
            opt3 = source.opt3,
            opt4 = source.opt4,

            isAnswer1 = source.isAnswer1,
            isAnswer2 = source.isAnswer2,
            isAnswer3 = source.isAnswer3,
            isAnswer4 = source.isAnswer4,
        ).apply {
            id.row = source.rowId
            id.quizId = source.quizId
        }
}

@Component
class MultiChoiceQCommandConverter: KConverter<MultiChoiceQ, MultiChoiceQCommand> {
    @Synchronized
    override fun convert(source: MultiChoiceQ): MultiChoiceQCommand =
        MultiChoiceQCommand().apply {
            copyBaseQuestionToCommand(this, source)

            opt1 = source.opt1
            opt2 = source.opt2
            opt3 = source.opt3
            opt4 = source.opt4

            isAnswer1 = source.isAnswer1
            isAnswer2 = source.isAnswer2
            isAnswer3 = source.isAnswer3
            isAnswer4 = source.isAnswer4
        }
}