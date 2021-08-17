package ir.dagger.quizak.services.db

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.QuizCommand
import ir.dagger.quizak.db.entity.quiz.Quiz

interface QuizService {
    fun saveQuiz(quizCommand: QuizCommand, user: ApplicationUser): QuizCommand
    fun findQuizCommandById(id: String, user: ApplicationUser): QuizCommand
    fun findQuizById(id: String, user: ApplicationUser): Quiz
}
