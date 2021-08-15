package ir.dagger.quizak.db.entity.quiz

import ir.dagger.quizak.db.entity.customers.User
import ir.dagger.quizak.db.entity.customers.UserInSession
import ir.dagger.quizak.db.entity.customers.UserSession
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class BaseAnswer(
    row: Int,
    @Enumerated(EnumType.ORDINAL)
    val type: QuizType,

    val startTime: LocalDateTime,
    val endTime: LocalDateTime
){
    @EmbeddedId
    var id: AnswerId = AnswerId(row)

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("session")
    lateinit var session: UserInSession
}

@Embeddable
data class AnswerId(
    @Column(
        name = "row_id",
        nullable = false,
    )
    var row: Int? = null,

    @Embedded
    @Column(
        name = "quiz_id",
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false,
    )
    var session: UserSession? = null,

): Serializable