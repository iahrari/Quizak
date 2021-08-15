package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.quiz.Quiz
import org.springframework.data.jpa.repository.JpaRepository

interface QuizRepository: JpaRepository<Quiz, String>