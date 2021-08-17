package ir.dagger.quizak.controller

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.MainEntityCommand
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

    @GetMapping("/{quizId}/edit")
    fun updateQuiz(
        model: Model,
        @PathVariable quizId: String,
        @AuthenticationPrincipal user: ApplicationUser
    ): String {
        val quiz = quizService.findQuizCommandById(quizId, user)
        model.addAttribute("quiz", quiz)
        model.addAttribute("mainEntity", quiz as MainEntityCommand)
        model.addAttribute("user", user)
        return "quiz/addQuiz"
    }

    @GetMapping("/new")
    fun newQuizPage(model: Model, @AuthenticationPrincipal user: ApplicationUser): String {
        val quiz = QuizCommand()
        model.addAttribute("quiz", quiz)
        model.addAttribute("mainEntity", quiz as MainEntityCommand)
        model.addAttribute("user", user)
        return "quiz/addQuiz"
    }

    @PostMapping("/new", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun newQuiz(
        @ModelAttribute quiz: QuizCommand,
        @AuthenticationPrincipal user: ApplicationUser,
    ): String {
        val q = quizService.saveQuiz(quiz, user)
        return "redirect:/quiz/${q.id}/show"
    }

    @GetMapping("/{quizId}/addQuestion/{quizType}")
    fun addQuestion(
        model: Model,
        @PathVariable quizId: String,
        @PathVariable quizType: QuizType,
        @AuthenticationPrincipal user: ApplicationUser
    ): String {
        model.addAttribute("user", user)
        return ""
    }
}