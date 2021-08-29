package ir.dagger.quizak.controller

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.MediaData
import ir.dagger.quizak.controller.command.UserCommand
import ir.dagger.quizak.services.db.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Controller
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/profile")
    fun showProfile(
        @AuthenticationPrincipal user: ApplicationUser,
        model: Model,
    ): String {
        val userCommand = userService.findUserById(user.id)
        model.addAttribute("media", userCommand.mediaData)
        model.addAttribute("userCommand", userCommand)
        model.addAttribute("user", user)
        return PROFILE_PAGE
    }

    @GetMapping("/profile/myCreations")
    fun showMyCreations(
        @AuthenticationPrincipal user: ApplicationUser,
        model: Model,
    ): String {
        val userCommand = userService.findUserById(user.id)

        model.addAttribute("myCreations", userService.myCreations(user))
        model.addAttribute("userCommand", userCommand)
        model.addAttribute("user", user)

        return "user/myCreations"
    }

    @PostMapping("/profile/update")
    fun updateProfile(
        @ModelAttribute @Valid userCommand: UserCommand,
        @ModelAttribute mediaData: MediaData,
        @AuthenticationPrincipal user: ApplicationUser,
        result: BindingResult
    ): String {
        val updatedUser = userService.updateUser(userCommand.apply {
            this.mediaData = mediaData }, user)
        user.apply {
            username = updatedUser.uniqueName
            mediaId = updatedUser.media?.id
        }

        if(result.hasErrors())
            return PROFILE_PAGE

        return "redirect:${PROFILE_PAGE}"
    }

    companion object {
        const val PROFILE_PAGE = "/user/profile"
    }
}