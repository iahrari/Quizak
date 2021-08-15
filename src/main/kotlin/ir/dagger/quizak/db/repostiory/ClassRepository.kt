package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.Class
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClassRepository: JpaRepository<Class, String> {
    fun findByUniqueName(name: String): Optional<Class>
}