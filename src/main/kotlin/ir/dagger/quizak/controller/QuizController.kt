package ir.dagger.quizak.controller

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.BaseQuestionCommand
import ir.dagger.quizak.controller.command.MainEntityCommand
import ir.dagger.quizak.controller.command.MediaData
import ir.dagger.quizak.controller.command.QuizCommand
import ir.dagger.quizak.db.entity.customers.Institute
import ir.dagger.quizak.db.entity.quiz.QuizType
import ir.dagger.quizak.services.db.QuizService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import kotlin.reflect.full.createInstance

@Controller
@RequestMapping("/quiz")
class QuizController(
    private val quizService: QuizService
) {
    @Autowired
    private lateinit var defaultInstitute: Institute

    @GetMapping("/{quizId}/show")
    fun showQuiz(
        model: Model,
        @PathVariable quizId: String,
        @AuthenticationPrincipal user: ApplicationUser,
    ): String {
        model.addAttribute("quiz", quizService.findQuizById(quizId, user))
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
        return "quiz/addQuiz"
    }

    @GetMapping("/new")
    fun newQuizPage(model: Model, @AuthenticationPrincipal user: ApplicationUser): String {
        val quiz = QuizCommand()
        model.addAttribute("quiz", quiz)
        model.addAttribute("media", quiz.mediaData)
        model.addAttribute("user", user)
        return "quiz/addQuiz"
    }

    @PostMapping("/new", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun newQuiz(
        @ModelAttribute quiz: QuizCommand,
        @ModelAttribute mediaData: MediaData,
        @AuthenticationPrincipal user: ApplicationUser,
    ): String {
        val q = quizService.saveQuiz(quiz.apply{ this.mediaData = mediaData }, user)
        return "redirect:/quiz/${q.id}/show"
    }

    @GetMapping("/{quizId}/addQuestion/{quizType}")
    fun addQuestionPage(
        model: Model,
        @PathVariable quizId: String,
        @PathVariable quizType: QuizType,
        @AuthenticationPrincipal user: ApplicationUser
    ): String {
        val question = QuizType.valueOf(quizType.name).questionCommandType
            .createInstance()
        question.quizId = quizId
        model.addAttribute("user", user)
        model.addAttribute("question", question)
        model.addAttribute("media", question.mediaData)

        return "quiz/AddQuestions"
    }

    @PostMapping(
        "/{quizId}/addQuestion/{quizType}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun addQuestion(
        @PathVariable quizId: String,
        @ModelAttribute question: BaseQuestionCommand,
        @ModelAttribute media: MediaData
    ): String {
        question.mediaData = media
        return "redirect:quiz/${quizId}/show"
    }
}