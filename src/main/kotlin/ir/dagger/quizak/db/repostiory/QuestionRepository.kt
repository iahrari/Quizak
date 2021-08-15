package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.quiz.BaseQuestion
import ir.dagger.quizak.db.entity.quiz.QuestionId
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository: JpaRepository<BaseQuestion, QuestionId> {
}