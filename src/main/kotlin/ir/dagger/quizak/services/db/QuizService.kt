package ir.dagger.quizak.services.db

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.QuizCommand
import ir.dagger.quizak.db.entity.quiz.Quiz
import org.springframework.web.multipart.MultipartFile

interface QuizService {
    fun saveQuiz(quizCommand: QuizCommand, user: ApplicationUser, imageFile: MultipartFile?): QuizCommand
    fun findQuizCommandById(id: String, user: ApplicationUser): QuizCommand
    fun findQuizById(id: String, user: ApplicationUser): Quiz
}
