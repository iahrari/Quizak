package ir.dagger.quizak.db.entity

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import ir.dagger.quizak.db.entity.base.BaseTimeBased
import ir.dagger.quizak.db.entity.base.BaseUniqueName
import ir.dagger.quizak.db.entity.base.MainEntity
import ir.dagger.quizak.db.entity.base.TimeStamps
import ir.dagger.quizak.db.entity.customers.Institute
import ir.dagger.quizak.db.entity.customers.User
import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.entity.quiz.QuizSession
import org.hibernate.annotations.NaturalId
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Class(
    uniqueName: String, name: String,
    @Column(nullable = false)
    var capacity: Int = 100,

    @Column(nullable = false)
    var settingsClosed: Boolean = false,

    @Column(nullable = false)
    var justAcceptedUsers: Boolean = false,

    @Column(nullable = false)
    val requirePhone: Boolean = false,

    @Embedded
    val timeBased: BaseTimeBased? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "teacher",
        referencedColumnName = "id"
    )
    var teacher: User? = null,
    var isPublicForAnyoneToUse: Boolean = false,
    var isPublicForAnyoneAddQuiz: Boolean = false,
    description: String? = null,
    media: Media? = null,
): BaseUniqueName(uniqueName, name, description, media) {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "institute",
        referencedColumnName = "id"
    )
    lateinit var institute: Institute

    @OneToMany(
        mappedBy = "classI",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    val acceptedUsers: MutableSet<AcceptedUser> = hashSetOf()

    @OneToMany(
        mappedBy = "classI",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    val registeredUsers: MutableSet<RegisterClass> = hashSetOf()

    @OneToMany(
        mappedBy = "classId",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    val classSessions: MutableSet<QuizSession> = hashSetOf()

    @OneToMany(
        mappedBy = "classId",
        cascade = [CascadeType.REMOVE],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private val classQuizList: MutableSet<Quiz> = hashSetOf()
    fun addQuiz(q: Quiz){
        q.createdBy = teacher!!
        q.institute = institute
        classQuizList.add(q)
    }

    fun getClassQuizList(): Set<Quiz> = classQuizList
}