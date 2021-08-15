package ir.dagger.quizak.db.entity.quiz

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import ir.dagger.quizak.db.entity.Class
import ir.dagger.quizak.db.entity.EntityUtil
import ir.dagger.quizak.db.entity.customers.User
import ir.dagger.quizak.db.entity.customers.UserInSession
import ir.dagger.quizak.db.entity.base.BaseEntity
import ir.dagger.quizak.db.entity.base.BaseTimeBased
import java.security.SecureRandom
import javax.persistence.*
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter

@Entity

class QuizSession(
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "startedBy",
        referencedColumnName = "id",
        nullable = false,
    )
    val startedBy: User,

    @Embedded
    @AttributeOverride(
        name = "startedTime",
        column = Column(nullable = false)
    )
    val baseTimeBased: BaseTimeBased,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "class",
        referencedColumnName = "id"
    )
    val classId: Class? = null,

    val endAtFirstAnswer: Boolean = false,

    val pinCode: String? = NanoIdUtils.randomNanoId(
        SecureRandom(),
        EntityUtil.numericAlphabet,
        6
    ),
    val answerSynchronized: Boolean = true,
): BaseEntity() {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        nullable = false,
        name = "quiz",
        referencedColumnName = "id"
    )
    lateinit var quizId: Quiz

    @OneToMany(
        mappedBy = "session",
        orphanRemoval = true,
        cascade = [CascadeType.ALL]
    )
    private val userSessionList: MutableSet<UserInSession> = hashSetOf()
    fun addUser(session: UserInSession) {
        session.session = this
        userSessionList.add(session)
    }

    fun getUsers(): Set<UserInSession> = userSessionList
    override fun toString(): String {
        return "QuizSession(startedBy=$startedBy, baseTimeBased=$baseTimeBased, classId=$classId, endAtFirstAnswer=$endAtFirstAnswer, pinCode=$pinCode, answerSynchronized=$answerSynchronized, quizId=$quizId)"
    }
}