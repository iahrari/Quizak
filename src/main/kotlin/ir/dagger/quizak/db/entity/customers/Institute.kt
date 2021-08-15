package ir.dagger.quizak.db.entity.customers

import ir.dagger.quizak.db.entity.Class
import ir.dagger.quizak.db.entity.Media
import ir.dagger.quizak.db.entity.base.BaseCustomer
import ir.dagger.quizak.db.entity.quiz.Quiz
import javax.persistence.*

/**
 * Institute is our paid customers.
 * They can create classes and hire teachers for holding quizes.
 * @author <a href="https://github.com/iahrari">Iman Ahrari</a>
 * @param insName is a natural key, simply a username like @quizak_ins,
 *  this parameter will be used for login in
 * @param name is a regular name that can be almost anything
 * @param phone is the contact phone number
 * @param email is for contacting institutes
 * @param address is of type {@link Address}
 * @param description should be a short introduction of the institute
 * @param salt is the result of bcrypt hashing process and will be used for authorization.
 * This parameter must not be exposed to any external apis
 * @param date is used to store some timestamps about creation, update and deletion of entity
 * @param media is profile picture of company and can be null
 * @param instituteId is generally calculated by this class itself
 */
@Entity(name = "Institute")
@Table(name = "institute")
class Institute(
    @Embedded
    val address: Address,
    uniqueName: String, salt: String, phone: String, email: String?, name: String,
    description: String? = null, media: Media? = null,
    isLocked: Boolean = false,
    ): BaseCustomer(uniqueName, salt, phone, email, name, description, isLocked, media){
    /**
     * List of teachers that are hired for this institution.
     */
    @OneToMany(
        mappedBy = "institute",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private val hired :MutableSet<HiredTeacher> = hashSetOf()
    fun addHiredTeacher(teacher: HiredTeacher){
        teacher.institute = this
        hired.add(teacher)
    }

    fun getHired(): Set<HiredTeacher> = hired

    /**
     * Classes that are being held by this institution.
     */
    @OneToMany(
        mappedBy = "institute",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER
    )
    private val classes: MutableSet<Class> = hashSetOf()
    fun addClass(c: Class) {
        c.institute = this
        classes.add(c)
    }

    fun getClasses(): Set<Class> = classes

    /**
     * Quizes that was made for this institution
     */
    @OneToMany(
        mappedBy = "institute",
        fetch = FetchType.LAZY
    )
    val quizes: Set<Quiz> = hashSetOf()

    override fun toString(): String {
        return "Institute(address=$address, ${super.toString()})"
    }
}