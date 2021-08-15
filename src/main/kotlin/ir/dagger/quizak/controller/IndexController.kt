package ir.dagger.quizak.controller

import ir.dagger.quizak.auth.ApplicationUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {

    @GetMapping("/", "", "/index.html", "/index")
    fun welcomePage(model: Model, @AuthenticationPrincipal user: ApplicationUser?): String {
        model.addAttribute("user", user)
        return "index"
    }
}