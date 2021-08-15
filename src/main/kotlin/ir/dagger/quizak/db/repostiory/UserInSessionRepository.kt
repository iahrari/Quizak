package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.customers.UserInSession
import ir.dagger.quizak.db.entity.customers.UserSession
import org.springframework.data.jpa.repository.JpaRepository

interface UserInSessionRepository: JpaRepository<UserInSession, UserSession> {
}