package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.customers.EmailVerification
import org.springframework.data.repository.CrudRepository

interface EmailVerificationRepository: CrudRepository<EmailVerification, String> {
}