package ir.dagger.quizak.db.entity

import ir.dagger.quizak.db.entity.base.TimeStamps
import ir.dagger.quizak.db.entity.customers.User
import java.io.Serializable
import javax.persistence.*

@Entity
data class AcceptedUser(
    @EmbeddedId
    val id: ClassUserId,
): TimeStamps(){
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    lateinit var user: User
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("classId")
    lateinit var classI: Class
}

@Entity
data class RegisterClass(
    @EmbeddedId
    val id: ClassUserId,

    var accepted: Boolean? = null
): TimeStamps(){
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    lateinit var user: User
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("classId")
    lateinit var classI: Class
}

@Embeddable
data class ClassUserId(
    @Column(
        name = "user_id",
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false
    )
    var userId: String,

    @Column(
        name = "class_id",
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false
    )
    var classId: String,
): Serializable