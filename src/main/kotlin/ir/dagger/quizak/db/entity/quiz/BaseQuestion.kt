package ir.dagger.quizak.db.entity.quiz

import ir.dagger.quizak.db.entity.base.TimeStamps
import ir.dagger.quizak.db.entity.Media
import org.hibernate.Hibernate
import java.io.Serializable
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class BaseQuestion(
    var question: String,
    var answerTime: Long,

    @Enumerated(EnumType.ORDINAL)
    val type: QuizType,

): TimeStamps(){
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("quizId")
    lateinit var quiz: Quiz

    @EmbeddedId
    val id: QuestionId = QuestionId()

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "picture",
        referencedColumnName = "id"
    )
    var media: Media? = null
}

@Embeddable
data class QuestionId(

    @Column(
        name = "quiz_id",
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false,
    )
    var quizId: String? = null,
): Serializable{
    @Column(
        name = "row_id",
        nullable = false,
    )
    var row: Int? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as QuestionId

        if (quizId != other.quizId) return false
        if (row != other.row) return false
        return true
    }

    override fun hashCode(): Int {
        var result: Int = quizId?.hashCode() ?: 0
        result = 31 * result + (row?.hashCode() ?: 0)
        return result
    }

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(quizId = $quizId , row = $row )"
    }
}