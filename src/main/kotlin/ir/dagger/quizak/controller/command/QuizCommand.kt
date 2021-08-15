package ir.dagger.quizak.controller.command

import org.springframework.web.multipart.MultipartFile

class QuizCommand: MainEntityCommand() {
    var classId: String? = null
    var private : Boolean = true
    var instituteId: String? = null
    var createdById: String? = null
    var imageFile: MultipartFile? = null
}