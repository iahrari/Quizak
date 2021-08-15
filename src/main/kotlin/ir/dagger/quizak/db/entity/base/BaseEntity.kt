package ir.dagger.quizak.db.entity.base

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.*

@MappedSuperclass
open class BaseEntity: TimeStamps() {
    @Id
    @GenericGenerator(
        name = "id",
        strategy = "ir.dagger.quizak.utils.NanoIdGenerator"
    )
    @GeneratedValue(generator = "id")
    @Column(
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false
    )
    var id: String? = null

    override fun toString(): String {
        return "BaseEntity(id='$id', ${super.toString()})"
    }

    override fun hashCode(): Int =
        id.hashCode()
}