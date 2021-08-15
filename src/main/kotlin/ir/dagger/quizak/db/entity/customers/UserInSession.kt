package ir.dagger.quizak.db.entity.customers

import ir.dagger.quizak.db.entity.quiz.BaseAnswer
import ir.dagger.quizak.db.entity.quiz.QuizSession
import java.io.Serializable
import javax.persistence.*

@Entity
data class UserInSession(
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    val user: User,
    val nickname: String,
){
    @EmbeddedId
    var userSessionId: UserSession = UserSession()

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("sessionId")
    lateinit var session: QuizSession
    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        mappedBy = "session",
    )
    private val answers: MutableSet<BaseAnswer> = hashSetOf()

    fun addAnswer(answer: BaseAnswer){
        answer.session = this
        answers.add(answer)
    }

    override fun hashCode(): Int = userSessionId.hashCode()
}

@Embeddable
data class UserSession(
    @Column(
        name = "user_id",
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false,
    )
    var userId: String? = null,
    @Column(
        name = "session_id",
        columnDefinition = "BINARY(21)",
        length = 21,
        nullable = false,
    )
    var sessionId: String? = null,
): Serializable {
    override fun hashCode(): Int =
        (userId + sessionId).hashCode()
}