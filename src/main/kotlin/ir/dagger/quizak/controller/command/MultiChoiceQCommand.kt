package ir.dagger.quizak.controller.command

import ir.dagger.quizak.db.entity.quiz.QuizType

class MultiChoiceQCommand: BaseQuestionCommand() {
    init {
        type = QuizType.MultiChoice
    }
    var opt1: String? = null
    var opt2: String? = null
    var opt3: String? = null
    var opt4: String? = null

    var isAnswer1: Boolean = true
    var isAnswer2: Boolean = true
    var isAnswer3: Boolean? = null
    var isAnswer4: Boolean? = null

    fun getIsAnswer1() = isAnswer1
    fun getIsAnswer2() = isAnswer2
    fun getIsAnswer3() = isAnswer3
    fun getIsAnswer4() = isAnswer4

    fun setIsAnswer1(isAnswer1: Boolean){
        this.isAnswer1 = isAnswer1
    }

    fun setIsAnswer2(isAnswer2: Boolean){
        this.isAnswer2 = isAnswer2
    }

    fun setIsAnswer3(isAnswer3: Boolean){
        this.isAnswer3 = isAnswer3
    }

    fun setIsAnswer4(isAnswer4: Boolean){
        this.isAnswer4 = isAnswer4
    }
}