package ir.dagger.quizak.services.db

import ir.dagger.quizak.auth.ApplicationUser
import ir.dagger.quizak.controller.command.BaseQuestionCommand
import ir.dagger.quizak.controller.command.QuizCommand
import ir.dagger.quizak.controller.command.converters.QuizCommandConverter
import ir.dagger.quizak.controller.command.converters.QuizConverter
import ir.dagger.quizak.controller.command.converters.UserCommandConverter
import ir.dagger.quizak.db.entity.MediaType
import ir.dagger.quizak.db.entity.quiz.QuestionId
import ir.dagger.quizak.db.entity.quiz.Quiz
import ir.dagger.quizak.db.repostiory.QuestionRepository
import ir.dagger.quizak.db.repostiory.QuizRepository
import ir.dagger.quizak.db.repostiory.UserRepository
import ir.dagger.quizak.services.FileService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import javax.transaction.Transactional
import javax.validation.Valid
import kotlin.reflect.full.createInstance

@Service
class QuizServiceImpl(
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository,
    private val userRepository: UserRepository,
    private val quizConverter: QuizConverter,
    private val quizCommandConverter: QuizCommandConverter,
    private val fileService: FileService,
    private val userCommandConverter: UserCommandConverter,
) : QuizService {

    @Transactional
    override fun saveQuiz(
        quizCommand: QuizCommand,
        user: ApplicationUser,
    ): QuizCommand {
        quizCommand.createdBy = userCommandConverter.convert(
            userRepository.findById(user.id).orElseThrow {
                HttpClientErrorException(HttpStatus.UNAUTHORIZED)
            }
        )
        val quiz = if (quizCommand.id == null) quizConverter.convert(quizCommand)
        else quizRepository.findById(quizCommand.id!!)
            .orElse(quizConverter.convert(quizCommand))

        quiz.id?.let {
            if (quiz.createdBy.id != user.id) throw HttpClientErrorException(HttpStatus.FORBIDDEN)
            quiz.name = quizCommand.name!!
            quiz.description = quizCommand.description!!
            quiz.isPrivate = quizCommand.private
        }

        quizCommand.mediaData.file?.let {
            if (!it.isEmpty)
                quiz.media = fileService.save(it, MediaType.Picture)
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
        if (
            quiz.classId.isPublicForAnyoneToUse
            || quiz.createdBy.id == user.id
            || (quiz.classId.teacher != null && quiz.classId.teacher!!.id == user.id)
            || quiz.classId.registeredUsers.any { it.id.userId == user.id }
        )
            return quiz
        else throw HttpClientErrorException(HttpStatus.FORBIDDEN)
    }

    @Transactional
    override fun deleteById(id: String, user: ApplicationUser) {
        val q = quizRepository.findById(id).orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND) }
        if (q.createdBy.id == user.id)
            quizRepository.deleteById(id)
        else throw HttpClientErrorException(HttpStatus.FORBIDDEN)
    }

    @Transactional
    override fun saveQuestion(
        @Valid questionCommand: BaseQuestionCommand,
        user: ApplicationUser,
    ): BaseQuestionCommand {
        val quiz = quizRepository.findById(questionCommand.quizId!!)
            .orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND) }

        if(quiz.createdBy.id == user.id){
            if(questionCommand.rowId == null)
                questionCommand.rowId = (quiz.getQuestions().stream()
                    .max { a, b -> a.id.row!! - b.id.row!! }.orElse(null)?.id?.row?: 0) + 1

            val question = questionCommand.type!!.converter
                .createInstance().convert(questionCommand)!!
            question.quiz = quiz
            questionCommand.mediaData.file?.let{
                if(!it.isEmpty)
                    question.media = fileService.save(it, MediaType.Picture)
                        .orElseThrow()
            }
            questionCommand.mediaData.mediaId?.let{
                question.media = fileService.findMediaById(it)
            }
            return question.type.commandConverter.createInstance()
                .convert(questionRepository.save(question))!!
        } else throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)
    }

    override fun deleteQuestionById(quizId: String, rowId: Int, user: ApplicationUser) {
        val question = questionRepository.findById(QuestionId(quizId).apply { this.row = rowId })
            .orElseThrow { (HttpClientErrorException(HttpStatus.NOT_FOUND)) }

        if(question.quiz.createdBy.id == user.id)
            questionRepository.delete(question)
        else throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)
    }

    override fun findQuestionById(quizId: String, row: Int, user: ApplicationUser): BaseQuestionCommand {
        val question = questionRepository.findById(QuestionId(quizId).apply { this.row = row })
            .orElseThrow { HttpClientErrorException(HttpStatus.NOT_FOUND) }

        if(question.quiz.createdBy.id == user.id)
            return question.type.commandConverter.createInstance().convert(question)!!
        else throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)
    }

}