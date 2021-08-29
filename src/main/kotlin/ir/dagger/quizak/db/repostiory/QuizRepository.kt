package ir.dagger.quizak.db.repostiory

import ir.dagger.quizak.db.entity.quiz.Quiz
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuizRepository: JpaRepository<Quiz, String>{
    @Query("select q from Quiz q where (q.classId.isPublicForAnyoneToUse=true or exists(select r from RegisterClass r where r.classI=q.classId and r.user.id=?1 and r.accepted=true)) and (q.createdBy.id=?1 or q.isPrivate=false) and (q.name like %?2% or q.description like %?3%)")
    fun findByNameContainingOrDescriptionContaining(
        userId: String?,
        name: String,
        description: String = name,
    ): List<Quiz>
}