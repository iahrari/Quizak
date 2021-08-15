package ir.dagger.quizak.services.db

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.QuizCommand
import ir.dagger.quizak.controller.command.converters.QuizCommandConverter
import ir.dagger.quizak.controller.command.converters.QuizConverter
import ir.dagger.quizak.db.entity.MediaType
import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.repostiory.ClassRepository
import ir.dagger.quizak.db.repostiory.QuizRepository
import ir.dagger.quizak.db.repostiory.UserRepository
import ir.dagger.quizak.services.FileService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import java.lang.RuntimeException
import javax.transaction.Transactional

@Service
class QuizServiceImpl(
    private val quizRepository: QuizRepository,
    private val classRepository: ClassRepository,
    private val userRepository: UserRepository,
    private val quizConverter: QuizConverter,
    private val quizCommandConverter: QuizCommandConverter,
    private val fileService: FileService,
): QuizService {
    @Transactional
    override fun saveQuiz(
        quizCommand: QuizCommand,
        user: ApplicationUser,
        imageFile: MultipartFile?): QuizCommand {
        quizCommand.createdById = user.id
        val quiz = if(quizCommand.id == null) quizConverter.convert(quizCommand)
                    else quizRepository.findById(quizCommand.id!!)
                        .orElse(quizConverter.convert(quizCommand))

        quiz.id?.let {
            if (quiz.createdBy.id != user.id) throw HttpClientErrorException(HttpStatus.FORBIDDEN)
            quiz.name = quizCommand.name!!
            quiz.description = quizCommand.description!!
            quiz.isPrivate = quizCommand.private
            //TODO: Add media
        }

        quizCommand.imageFile?.let {
            quiz.media = fileService.save(quizCommand.imageFile!!, MediaType.Picture)
                .orElseThrow()
        }
        return quizCommandConverter.convert(quizRepository.save(quiz!!))
    }

    override fun findQuizCommandById(id: String, user: ApplicationUser): QuizCommand {
//        TODO: Exception handling
        return quizCommandConverter.convert(findQuizById(id, user))
    }

    override fun findQuizById(id: String, user: ApplicationUser): Quiz {
        val quiz = quizRepository.findById(id).orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND) }
        if(
            quiz.classId.isPublicForAnyoneToUse
            || quiz.createdBy.id == user.id
            || (quiz.classId.teacher != null && quiz.classId.teacher!!.id == user.id)
            || quiz.classId.registeredUsers.any { it.id.userId == user.id })
            return quiz
        else throw HttpClientErrorException(HttpStatus.FORBIDDEN)
    }

}