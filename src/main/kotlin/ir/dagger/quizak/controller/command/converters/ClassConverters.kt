package ir.dagger.quizak.controller.command.converters

import ir.dagger.quizak.controller.command.ClassCommand
import ir.dagger.quizak.db.entity.Class
import org.springframework.stereotype.Component

@Component
class ClassCommandConverter: KConverter<Class, ClassCommand>{
    @Synchronized
    override fun convert(source: Class): ClassCommand =
        ClassCommand().apply {
            copyBaseUniqueNameCommandData(source, this)
        }

}