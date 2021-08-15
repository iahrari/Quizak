package ir.dagger.quizak.db.entity.base

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@MappedSuperclass
open class TimeStamps(
    @JsonIgnore
//    @CreationTimestamp
    var createdAt: LocalDateTime? = null,

    @JsonIgnore
//    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,

    @JsonIgnore
//    @Column(columnDefinition = "TIMESTAMP")
    var deletedAt: LocalDateTime? = null
) {
    @get:Transient
    @get:JsonProperty("createdAt")
    val createdAtZone: ZonedDateTime
         get() = ZonedDateTime.of(createdAt, TimeZone.getDefault().toZoneId())
             .withZoneSameInstant(ZoneId.of("UTC"))

    @get:Transient
    @get:JsonProperty("updatedAt")
    val updatedAtZone: ZonedDateTime
        get() = ZonedDateTime.of(updatedAt, TimeZone.getDefault().toZoneId())
            .withZoneSameInstant(ZoneId.of("UTC"))

    @get:Transient
    @get:JsonProperty("deletedAt")
    val deletedAtZone: ZonedDateTime?
        get() = if (deletedAt != null)
            ZonedDateTime.of(deletedAt, TimeZone.getDefault().toZoneId())
                .withZoneSameInstant(ZoneId.of("UTC")) else null

    @PrePersist
    fun prePersist(){
        createdAt = LocalDateTime.now()
    }

    @PreUpdate
    fun preUpdate(){
        updatedAt = LocalDateTime.now()
    }

    override fun toString(): String {
        return "TimeStamps(createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt)"
    }
}