package ir.dagger.quizak.services.db

import ir.dagger.quizak.db.entity.quiz.Quiz

interface SearchService {
    fun searchQuizContaining(query: String, userId: String? ): List<Quiz>
}