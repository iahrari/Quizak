package ir.dagger.quizak.controller.command.converters

import ir.dagger.quizak.controller.command.BaseCustomerCommand
import ir.dagger.quizak.controller.command.BaseEntityCommand
import ir.dagger.quizak.controller.command.MainEntityCommand
import ir.dagger.quizak.db.entity.base.BaseCustomer
import ir.dagger.quizak.db.entity.base.BaseEntity
import ir.dagger.quizak.db.entity.base.MainEntity
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class BaseEntityCommandConverter:
    Converter<BaseEntity, BaseEntityCommand>{
    @Synchronized
    override fun convert(source: BaseEntity): BaseEntityCommand =
        BaseEntityCommand(source.id)
}

@Component
class MainEntityCommandConverter(
    private val baseEntityCommandConverter: BaseEntityCommandConverter
): Converter<MainEntity, MainEntityCommand> {
    @Synchronized
    override fun convert(source: MainEntity): MainEntityCommand =
        (baseEntityCommandConverter.convert(source) as MainEntityCommand)
            .apply {
                name = source.name
                description = source.description
            }
}

@Component
class BaseCustomerCommandConverter(
   private val mainEntityCommandConverter: MainEntityCommandConverter
): Converter<BaseCustomer, BaseCustomerCommand> {
    @Synchronized
    override fun convert(source: BaseCustomer): BaseCustomerCommand =
        (mainEntityCommandConverter.convert(source) as BaseCustomerCommand)
            .apply {
                uniqueName = source.uniqueName
                phone = source.phone
                email = source.email
                isExpired = source.isExpired
                isEnabled = source.isEnabled
            }
}
