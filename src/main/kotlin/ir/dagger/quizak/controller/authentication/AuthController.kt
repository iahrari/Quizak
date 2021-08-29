package ir.dagger.quizak.controller.authentication

import ir.dagger.quizak.controller.command.AuthCommand
import ir.dagger.quizak.services.db.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import javax.validation.Valid

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
        model.addAttribute("user", AuthCommand())
        return SIGNUP_FORM
    }

    @PostMapping("/signup")
    fun signup(
        @ModelAttribute("user") @Valid user: AuthCommand,
        userResult: BindingResult,
    ): String {
        if(userResult.hasErrors()) return SIGNUP_FORM
        userService.createUser(user, user.password!!)
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

    companion object {
        const val SIGNUP_FORM = "authentication/signup"
    }
}