package ir.dagger.quizak.services.db

import ir.dagger.quizak.controller.command.QuizCommand
import ir.dagger.quizak.controller.command.converters.QuizCommandConverter
import ir.dagger.quizak.db.repostiory.QuizRepository
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(
    private val quizRepository: QuizRepository,
    private val quizCommandConverter: QuizCommandConverter,
): SearchService {
    override fun searchQuizContaining(query: String, userId: String?): List<QuizCommand> =
        quizRepository.findByNameContainingOrDescriptionContaining(userId, query)
            .map(quizCommandConverter::convert)
}