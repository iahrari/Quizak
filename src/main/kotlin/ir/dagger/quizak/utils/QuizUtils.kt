package ir.dagger.quizak.utils

import ir.dagger.quizak.controller.command.BaseQuestionCommand
import ir.dagger.quizak.controller.command.MultiChoiceQCommand
import ir.dagger.quizak.controller.command.TrueFalseQCommand
import ir.dagger.quizak.db.entity.quiz.QuizType

fun setBaseQuestionFromParam(
    map: Map<String, Array<String>>,
    question: BaseQuestionCommand
){
    question.apply{
        rowId = map["rowId"]?.get(0)?.toIntOrNull()
        quizId = map["quizId"]!![0]
        type = QuizType.valueOf(map["type"]!![0])
        this.question = map["question"]?.get(0)
        answerTime = map["answerTime"]!![0].toLong()
    }
}

fun setMultiChoiceFromParam(
    map: Map<String, Array<String>>,
    question: BaseQuestionCommand
): MultiChoiceQCommand =
    (question as MultiChoiceQCommand).apply {
        setBaseQuestionFromParam(map, this)

        opt1 = map["opt1"]!![0]
        opt2 = map["opt2"]!![0]
        opt3 = map["opt3"]?.get(0)
        opt4 = map["opt4"]?.get(0)

        isAnswer1 = (map["isAnswer1"]?: map["_isAnswer1"])!![0].toBoolean()
        isAnswer2 = (map["isAnswer2"]?: map["_isAnswer2"])!![0].toBoolean()
        isAnswer3 = (map["isAnswer3"]?: map["_isAnswer3"])?.get(0).toBoolean()
        isAnswer4 = (map["isAnswer4"]?: map["_isAnswer4"])?.get(0).toBoolean()
    }

fun setTrueFalseFromParam(
    map: Map<String, Array<String>>,
    question: BaseQuestionCommand
): TrueFalseQCommand =
    (question as TrueFalseQCommand).apply {
        setBaseQuestionFromParam(map, this)
        correct = (map["correct"]?: map["_correct"])!![0].toBoolean()
    }