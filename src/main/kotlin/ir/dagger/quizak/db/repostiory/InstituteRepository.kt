package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.customers.Institute
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InstituteRepository: JpaRepository<Institute, String> {
    abstract fun findByUniqueName(uniqueName: String): Optional<Institute>
}