package ir.dagger.quizak.db.entity.base

import com.fasterxml.jackson.annotation.JsonIgnore
import ir.dagger.quizak.db.entity.Media
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseCustomer(
    uniqueName: String,

    @Column(
        length = 12,
        unique = true,
        nullable = false
    )
    var phone: String?,

    @Column(
        name = "email",
        unique = true,
        nullable = false
    )
    var email: String?,
    name: String,
    description: String? = null,
    var isLocked: Boolean = false,
    media: Media? = null,

) : BaseUniqueName(uniqueName, name, description, media){
    var isEnabled: Boolean = false
    var isExpired: Boolean = false

    @JsonIgnore
    @Column(
        name = "hash",
        columnDefinition = "BINARY(60)",
        length = 60,
        nullable = false,
    )
    lateinit var hash: String

    override fun toString(): String {
        return "BaseCustomer(uniqueName='$uniqueName', hash='${hash.replace(0.toChar().toString(), "")}', phone=$phone, email=$email, ${super.toString()}"
    }
}