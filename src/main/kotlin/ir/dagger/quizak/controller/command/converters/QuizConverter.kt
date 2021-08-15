package ir.dagger.quizak.controller.command.converters

import ir.dagger.quizak.controller.command.QuizCommand
import ir.dagger.quizak.db.entity.Class
import ir.dagger.quizak.db.entity.customers.Institute
import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.repostiory.ClassRepository
import ir.dagger.quizak.db.repostiory.InstituteRepository
import ir.dagger.quizak.db.repostiory.UserRepository
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class QuizConverter(
    private val userRepository: UserRepository,
    private val classRepository: ClassRepository,
    private val instituteRepository: InstituteRepository,
    private val defaultClass: Class,
    private val defaultInstitute: Institute
): Converter<QuizCommand, Quiz> {

    //TODO: Add exception handling
    //TODO: Add media conversion
    @Synchronized
    override fun convert(source: QuizCommand): Quiz =
        Quiz(
            classId = if(source.classId == null) defaultClass
                        else classRepository.findById(source.classId!!).orElseThrow(),
            name = source.name!!,
            isPrivate = source.private,
            description = source.description,
        ).apply {
            id = source.id
            createdBy = userRepository.findById(source.createdById!!).orElseThrow()
            institute = if(source.instituteId == null) defaultInstitute
                        else instituteRepository.findById(source.instituteId!!).orElseThrow()
        }
}

@Component
class QuizCommandConverter: Converter<Quiz, QuizCommand>{
    override fun convert(source: Quiz): QuizCommand =
        QuizCommand().apply {
            private = source.isPrivate
            classId = source.classId.id
            if(source.isInstituteInitialized())
                instituteId = source.institute.id
            if(source.isCreatedByInitialized())
                createdById = source.createdBy.id
            name = source.name
            description = source.description
            id = source.id
        }

}