package ir.dagger.quizak.db.entity.base

import ir.dagger.quizak.db.entity.Media
import org.hibernate.annotations.NaturalId
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseUniqueName(
    @NaturalId
    @Column(
        unique = true,
        nullable = false,
        length = 36,
    )
    var uniqueName: String,
    name: String,
    description: String? = null,
    media: Media? = null,
): MainEntity(name, description, media) {
}