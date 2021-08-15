package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.Media
import org.springframework.data.jpa.repository.JpaRepository

interface MediaRepository: JpaRepository<Media, String> {
}