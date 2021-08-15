package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.customers.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, String>{
    fun findByEmail(email: String): Optional<User>
    fun findByUniqueName(name: String): Optional<User>
}