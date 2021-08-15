package ir.dagger.quizak.db.entity.base

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class BaseTimeBased(
    var endTime: LocalDateTime? = null,
    var startedTime: LocalDateTime? = null,
)