package ir.dagger.quizak.db.entity.customers

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class UserProf(
    @EmbeddedId
    val content: UserProfE,
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    val user: User
)

@Entity
data class HiredTeacher(
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    val user: User,

    val startedAt: LocalDateTime,
    var endedAt: LocalDateTime,
    val fullTime: Boolean = false,
){
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("insId")
    lateinit var institute: Institute

    @EmbeddedId
    var id: UserInsId = UserInsId()
}

@Embeddable
data class UserProfE(
    @Column(nullable = false)
    var profession: String,
    @Column(
        name = "user_id",
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false,
    )
    var userId: String? = null,
): Serializable

@Embeddable
data class UserInsId(
    @Column(
        name = "user_id",
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false,
    )
    var userId: String? = null,
    @Column(
        name = "ins_id",
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false,
    )
    var insId: String? = null
): Serializable