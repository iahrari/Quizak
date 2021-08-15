package ir.dagger.quizak.utils

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable

class NanoIdGenerator: IdentifierGenerator {
    override fun generate(s: SharedSessionContractImplementor?, obj: Any?): Serializable =
        NanoIdUtils.randomNanoId()

}