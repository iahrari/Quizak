package ir.dagger.quizak.controller.command.converters

import ir.dagger.quizak.controller.command.BaseCustomerCommand
import ir.dagger.quizak.controller.command.BaseEntityCommand
import ir.dagger.quizak.controller.command.BaseUniqueNameCommand
import ir.dagger.quizak.controller.command.MainEntityCommand
import ir.dagger.quizak.db.entity.base.BaseCustomer
import ir.dagger.quizak.db.entity.base.BaseEntity
import ir.dagger.quizak.db.entity.base.BaseUniqueName
import ir.dagger.quizak.db.entity.base.MainEntity

fun copyBaseEntityCommandData(source: BaseEntity, destination: BaseEntityCommand){
    destination.apply {
        id = source.id
    }
}

fun copyMainEntityCommandData(source: MainEntity, destination: MainEntityCommand){
    destination.apply {
        copyBaseEntityCommandData(source, destination)
        name = source.name
        description = source.description
        mediaData.mediaId = source.media?.id
    }
}

fun copyBaseUniqueNameCommandData(source: BaseUniqueName, destination: BaseUniqueNameCommand){
    destination.apply {
        copyMainEntityCommandData(source, destination)
        uniqueName = source.uniqueName
    }
}

fun copyBaseCustomerCommandData(source: BaseCustomer, destination: BaseCustomerCommand){
    destination.apply {
        copyBaseUniqueNameCommandData(source, destination)
        phone = source.phone
        email = source.email
        isExpired = source.isExpired
        isEnabled = source.isEnabled
    }
}
