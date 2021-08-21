package ir.dagger.quizak.services.db

import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.repostiory.QuizRepository
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(
    private val quizRepository: QuizRepository
): SearchService {
    override fun searchQuizContaining(query: String, userId: String?): List<Quiz> =
        quizRepository.findByNameContainingOrDescriptionContaining(userId, query)
}