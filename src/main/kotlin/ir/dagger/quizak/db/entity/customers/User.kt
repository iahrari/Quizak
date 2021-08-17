package ir.dagger.quizak.db.entity.customers

import ir.dagger.quizak.db.entity.AcceptedUser
import ir.dagger.quizak.db.entity.Class
import ir.dagger.quizak.db.entity.Media
import ir.dagger.quizak.db.entity.RegisterClass
import ir.dagger.quizak.db.entity.base.BaseCustomer
import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.entity.quiz.QuizSession
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "user")
@AttributeOverrides(
    AttributeOverride(name = "phone", column = Column(nullable = true, length = 12, unique = true,)),
    AttributeOverride(name = "email", column = Column(nullable = true, unique = true))
)
class User(
    @Column(
        name = "family_name",
        length = 200,
    )
    var familyName: String,

    @Column(name = "born_at")
    var bornAt: LocalDate?,

    uniqueName: String, phone: String?,

    email: String?, name: String,

    description: String? = null, media: Media? = null,

    @Column(
        name = "verified_teacher",
        nullable = false,
    )
    var verifiedTeacher: Boolean = false,

    @Column(
        name = "is_teacher",
        nullable = false,
    )
    var isTeacher: Boolean = false,
    isLocked: Boolean = false,
): BaseCustomer(uniqueName, phone, email, name, description, isLocked, media){

    @OneToMany(
        mappedBy = "user",
        orphanRemoval = true,
    )
    val userSessionList: Set<UserInSession> = hashSetOf()

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private val userProfessions: MutableSet<UserProf> = hashSetOf()
    fun addProfession(prof: String) = userProfessions.apply {
        add(UserProf(UserProfE(prof), this@User))
    }

    fun getUserProfessions(): Set<UserProf> = userProfessions

    @OneToMany(
        mappedBy = "user",
        orphanRemoval = true,
    )
    val acceptedUsers: Set<AcceptedUser> = hashSetOf()

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    val registeredClasses: MutableSet<RegisterClass> = hashSetOf()

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    val hired: Set<HiredTeacher> = hashSetOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "teacher"
    )
    val teachInClasses: Set<Class> = hashSetOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user"
    )
    val quizSession: Set<UserInSession> = hashSetOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "startedBy"
    )
    val startedByMy: MutableSet<QuizSession> = hashSetOf()

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "createdBy"
    )
    private val createdByMe: MutableSet<Quiz> = hashSetOf()
    fun getCreatedBy(): Set<Quiz> = createdByMe

    override fun toString(): String {
        return "User(familyName='$familyName', bornAt=$bornAt, verifiedTeacher=$verifiedTeacher, isTeacher=$isTeacher, ${super.toString()}"
    }
}