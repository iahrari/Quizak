package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.quiz.QuizSession
import org.springframework.data.jpa.repository.JpaRepository

interface QuizSessionRepository: JpaRepository<QuizSession, String> {
}