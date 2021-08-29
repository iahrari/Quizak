package ir.dagger.quizak.controller.command.converters

import ir.dagger.quizak.controller.command.QuizCommand
import ir.dagger.quizak.db.entity.Class
import ir.dagger.quizak.db.entity.customers.Institute
import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.repostiory.ClassRepository
import ir.dagger.quizak.db.repostiory.InstituteRepository
import ir.dagger.quizak.db.repostiory.UserRepository
import org.springframework.stereotype.Component
import kotlin.reflect.full.createInstance

@Component
class QuizConverter(
    private val userRepository: UserRepository,
    private val classRepository: ClassRepository,
    private val instituteRepository: InstituteRepository,
    private val defaultClass: Class,
    private val defaultInstitute: Institute
): KConverter<QuizCommand, Quiz> {

    //TODO: Add media conversion
    @Synchronized
    override fun convert(source: QuizCommand): Quiz =
        Quiz(
            classId = if(source.classId == null) defaultClass
                        else classRepository.findById(source.classId!!.id!!).orElseThrow(),
            name = source.name!!,
            isPrivate = source.private,
            description = source.description,
        ).apply {
            id = source.id
            createdBy = userRepository.findById(source.createdBy!!.id!!).orElseThrow()
            institute = if(source.instituteId == null) defaultInstitute
                        else instituteRepository.findById(source.instituteId!!).orElseThrow()
        }
}

@Component
class QuizCommandConverter(
    private val classCommandConverter: ClassCommandConverter,
    private val userCommandConverter: UserCommandConverter,
): KConverter<Quiz, QuizCommand>{
    override fun convert(source: Quiz): QuizCommand =
        QuizCommand().apply {
            copyMainEntityCommandData(source, this)
            private = source.isPrivate
            classId = classCommandConverter.convert(source.classId)
            if(source.isInstituteInitialized())
                instituteId = source.institute.id
            if(source.isCreatedByInitialized())
                createdBy = userCommandConverter.convert(source.createdBy)

            questions = source.getQuestions().map {
                it.type.commandConverter.createInstance().convert(it)!!
            }
        }
}