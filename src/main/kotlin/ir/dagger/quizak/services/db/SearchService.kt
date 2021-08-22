package ir.dagger.quizak.services.db

import ir.dagger.quizak.controller.command.QuizCommand

interface SearchService {
    fun searchQuizContaining(query: String, userId: String? ): List<QuizCommand>
}