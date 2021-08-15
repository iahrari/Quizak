package ir.dagger.quizak.db.entity.customers

import ir.dagger.quizak.db.entity.base.BaseEntity
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class EmailVerification(
    val email: String,
    @OneToOne
    val user: User,
    var isEnabled: Boolean = false
): BaseEntity()