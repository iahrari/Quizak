package ir.dagger.quizak.controller

import ir.dagger.quizak.auth.ApplicationUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class DefaultExceptionHandler {

    @ExceptionHandler(HttpClientErrorException::class)
    fun clientError(
        @AuthenticationPrincipal user: ApplicationUser,
        response: HttpServletResponse,
        e: HttpClientErrorException,
        model: Model
    ): String {
        response.status = e.statusCode.value()

        model.addAttribute("status", response.status)
        model.addAttribute("text", e.statusText)
        model.addAttribute("user", user)
        return "error"
    }
}