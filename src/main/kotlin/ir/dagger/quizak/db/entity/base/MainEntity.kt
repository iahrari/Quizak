package ir.dagger.quizak.db.entity.base

import ir.dagger.quizak.db.entity.Media
import javax.persistence.*

@MappedSuperclass
open class MainEntity(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var description: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "picture",
        referencedColumnName = "id"
    )
    var media: Media? = null,
): BaseEntity(){
    override fun toString(): String {
        return "MainEntity(name='$name', description=$description, media=$media, ${super.toString()})"
    }
}