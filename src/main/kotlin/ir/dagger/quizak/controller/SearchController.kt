package ir.dagger.quizak.controller

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.db.repostiory.QuizRepository
import ir.dagger.quizak.services.db.SearchService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class SearchController(
    private val searchService: SearchService
) {
    @GetMapping("/search")
    fun search(
        @RequestParam("search") search: String,
        @AuthenticationPrincipal user: ApplicationUser?,
        model: Model
    ): String {
        val quizzes = searchService.searchQuizContaining(search, user?.id)
        model.addAttribute("user", user)
        model.addAttribute("quizzes", quizzes)
        return "search"
    }
}