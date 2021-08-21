package ir.dagger.quizak.db.entity.quiz

import ir.dagger.quizak.db.entity.Media
import ir.dagger.quizak.db.entity.base.MainEntity
import javax.persistence.*
import ir.dagger.quizak.db.entity.Class
import ir.dagger.quizak.db.entity.customers.Institute
import ir.dagger.quizak.db.entity.customers.User


@Entity(name = "Quiz")
@Table(name = "quiz")
class Quiz(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "class",
        referencedColumnName = "id",
        nullable = false
    )
    val classId: Class,

    name: String,

    @Column(nullable = false)
    var isPrivate: Boolean = true,
    description: String? = null,
    media: Media? = null,
): MainEntity(name, description, media){
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "institute",
        referencedColumnName = "id",
        nullable = false
    )
    lateinit var institute: Institute

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "created_by",
        referencedColumnName = "id",
        nullable = false,
    )
    lateinit var createdBy: User

    @OneToMany(
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        mappedBy = "quizId",
        fetch = FetchType.EAGER
    )
    private val sessions: MutableSet<QuizSession> = hashSetOf()
    fun addSession(s: QuizSession){
        s.quizId = this
        sessions.add(s)
    }

    fun getSessions(): Set<QuizSession> = sessions


    @OneToMany(cascade = [CascadeType.REMOVE], fetch = FetchType.EAGER, mappedBy = "quiz")
    private val questions: MutableSet<BaseQuestion> = hashSetOf()
    fun addQuestion(question: BaseQuestion){
        question.quiz = this
//        question.id.quizId = id
        question.id.row = questions.size
        questions.add(question)
    }

    fun getQuestions(): Set<BaseQuestion> = questions

    fun isInstituteInitialized() =
        this::institute.isInitialized

    fun isCreatedByInitialized() =
        this::createdBy.isInitialized
}