package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.Media
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MediaRepository: JpaRepository<Media, String> {
    fun findByFolder1AndFolder2AndFileName(
        folder1: String,
        folder2: String,
        fileName: String
    ): Optional<Media>

}