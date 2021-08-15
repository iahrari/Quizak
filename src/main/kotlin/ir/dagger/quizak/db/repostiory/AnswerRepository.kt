package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.quiz.AnswerId
import ir.dagger.quizak.db.entity.quiz.BaseAnswer
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository: JpaRepository<BaseAnswer, AnswerId>