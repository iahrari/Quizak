package ir.dagger.quizak.controller

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.*
import ir.dagger.quizak.db.entity.quiz.QuizType
import ir.dagger.quizak.services.db.QuizService
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.DirectFieldBindingResult
import org.springframework.validation.SmartValidator
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import kotlin.reflect.full.createInstance

@Controller
@RequestMapping("/quiz")
class QuizController(
    private val quizService: QuizService,
    private val validator: SmartValidator,
) {

    @GetMapping("/{quizId}/show")
    fun showQuiz(
        model: Model,
        @PathVariable quizId: String,
        @AuthenticationPrincipal user: ApplicationUser,
    ): String {
        model.addAttribute("quiz", quizService.findQuizCommandById(quizId, user))
        model.addAttribute("user", user)
        return "quiz/showQuiz"
    }

    @GetMapping("/{quizId}/delete")
    fun deleteQuiz(
        @PathVariable quizId: String,
        @AuthenticationPrincipal user: ApplicationUser,
    ): String {
        quizService.deleteById(quizId, user)
        return "redirect:/user/profile/myCreations"
    }

    @GetMapping("/{quizId}/edit")
    fun updateQuiz(
        model: Model,
        @PathVariable quizId: String,
        @AuthenticationPrincipal user: ApplicationUser
    ): String {
        val quiz = quizService.findQuizCommandById(quizId, user)
        model.addAttribute("quiz", quiz)
        model.addAttribute("media", quiz.mediaData)
        model.addAttribute("user", user)
        return QUIZ_ADD_FORM
    }

    @GetMapping("/new")
    fun newQuizPage(model: Model, @AuthenticationPrincipal user: ApplicationUser): String {
        val quiz = QuizCommand()
        model.addAttribute("quiz", quiz)
        model.addAttribute("media", quiz.mediaData)
        model.addAttribute("user", user)
        return QUIZ_ADD_FORM
    }

    @PostMapping("/new", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun newQuiz(
        @AuthenticationPrincipal user: ApplicationUser,
        @ModelAttribute("media") mediaData: MediaData,
        @ModelAttribute("quiz") @Valid quiz: QuizCommand,
        result: BindingResult,
        model: Model
    ): String {
        if(result.hasErrors()) {
            model.addAttribute("user", user)
            return QUIZ_ADD_FORM
        }
        val q = quizService.saveQuiz(quiz.apply{ this.mediaData = mediaData }, user)
        return "redirect:/quiz/${q.id}/show"
    }

    @Suppress("SpringMVCViewInspection")
    @GetMapping("/{quizId}/addQuestion/{quizType}")
    fun addQuestionPage(
        model: Model,
        @PathVariable quizId: String,
        @PathVariable quizType: QuizType,
        @AuthenticationPrincipal user: ApplicationUser,
        request: HttpServletRequest
    ): String {
        val question = quizType.questionCommandType
            .createInstance()
        question.quizId = quizId
        model.addAttribute("question", question)
        model.addAttribute("user", user)
        model.addAttribute("media", question.mediaData)

        return addQuestionForm(quizType.name)
    }

    @Suppress("SpringMVCViewInspection")
    @GetMapping("/{quizId}/updateQuestion/{rowId}")
    fun updateQuestionPage(
        model: Model,
        @PathVariable quizId: String,
        @PathVariable rowId: Int,
        @AuthenticationPrincipal user: ApplicationUser,
        request: HttpServletRequest
    ): String {
        val question = quizService.findQuestionById(quizId, rowId, user)
        question.quizId = quizId
        model.addAttribute("question", question)
        model.addAttribute("user", user)
        model.addAttribute("media", question.mediaData)

        return addQuestionForm(question.type!!.name)
    }

    @PostMapping(
        "/{quizId}/addQuestion/{quizType}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun addQuestion(
        @PathVariable quizId: String,
        @PathVariable quizType: QuizType,
        @AuthenticationPrincipal user: ApplicationUser,
        @ModelAttribute("media") media: MediaData,
        result: BindingResult,
        request: HttpServletRequest,
        model: Model
    ): String {
        println("Request param: ${request.parameterMap}")
        val question = quizType.questionCommandType.createInstance()
        question.mediaData = media
        quizType.paramFunction(request.parameterMap, question)

        val questionResult = DirectFieldBindingResult(question, "question")
        validator.validate(question, questionResult)

        if(result.hasErrors() || questionResult.hasErrors()) {
            question.quizId = quizId
            model.addAttribute("org.springframework.validation.BindingResult.question", questionResult)
            model.addAttribute("user", user)
            model.addAttribute("question", question)
            return addQuestionForm(question.type!!.name)
        }
        quizService.saveQuestion(question, user)
        return "redirect:/quiz/${quizId}/show"
    }

    @GetMapping("/{quizId}/deleteQuestion/{rowId}")
    fun deleteQuestion(
        @PathVariable quizId: String,
        @PathVariable rowId: Int,
        @AuthenticationPrincipal user: ApplicationUser
    ): String {
        quizService.deleteQuestionById(quizId, rowId, user)
        return "redirect:/quiz/${quizId}/show"
    }

    companion object{
        const val QUIZ_ADD_FORM= "quiz/addQuiz"
        fun addQuestionForm(name: String): String =
            "quiz/AddQuestions$name"
    }
}