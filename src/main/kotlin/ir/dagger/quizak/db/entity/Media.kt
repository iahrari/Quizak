package ir.dagger.quizak.db.entity

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import ir.dagger.quizak.db.entity.base.BaseEntity
import ir.dagger.quizak.db.entity.customers.Institute
import ir.dagger.quizak.db.entity.customers.User
import ir.dagger.quizak.db.entity.quiz.BaseQuestion
import ir.dagger.quizak.db.entity.quiz.Quiz
import javax.persistence.*

@Entity
@Table(
    name = "media",
    uniqueConstraints = [
        UniqueConstraint(name = "path", columnNames = ["folder1", "folder2", "filename"])
    ]
)
data class Media(
    @Column(
        name = "folder1",
        columnDefinition = "BINARY(2)",
        nullable = false
    )
    val folder1: String,
    @Column(
        name = "folder2",
        columnDefinition = "BINARY(2)",
        nullable = false
    )
    val folder2: String,
    @Column(
        name = "filename",
        columnDefinition = "BINARY(60)",
        nullable = false
    )
    val fileName: String,

    @Enumerated(EnumType.STRING)
    val type: MediaType,

): BaseEntity(){
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "media",
    )
    val users: MutableSet<User> = hashSetOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "media",
    )
    val quizes: MutableSet<Quiz> = hashSetOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "media",
    )
    val institutes: MutableSet<Institute> = hashSetOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "media",
    )
    val classes: MutableSet<Class> = hashSetOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "media",
    )
    val questions: MutableSet<BaseQuestion> = hashSetOf()
    override fun toString(): String {
        return "Media(folder1='$folder1', folder2='$folder2', fileName='$fileName', type=$type)"
    }


}