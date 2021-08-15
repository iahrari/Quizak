package ir.dagger.quizak.controller.authentication

import ir.dagger.quizak.controller.command.UserCommand
import ir.dagger.quizak.services.db.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.lang.RuntimeException

@Controller
class AuthController(
    private val userService: UserService
) {

    @GetMapping("/login")
    fun loginPage(): String {
        return "authentication/login"
    }

    @GetMapping("/signup")
    fun signupForm(model: Model): String {
        model.addAttribute("user", UserCommand())
        return "authentication/signup"
    }

    @PostMapping("/signup")
    fun signup(@ModelAttribute user: UserCommand): String {
        userService.createUser(user)
        return "redirect:/verifyEmail/id/${user.uniqueName}"
    }

    @GetMapping("/verifyEmail/id/{uniqueName}")
    fun verifyEmailPage(model: Model, @PathVariable uniqueName: String): String{
        val user = userService.findUserByUniqueName(uniqueName)
        //TODO: You know what you need to do
        if(user.isEnabled || user.isExpired) throw RuntimeException("Not a valid request")
        return "authentication/verifyEmail"
    }

    @GetMapping("/verifyEmail/{id}")
    fun verifyEmail(model: Model, @PathVariable id: String): String{
        userService.verifyEmail(id)
        //TODO: You know what you need to do

        return "redirect:/login"
    }
}