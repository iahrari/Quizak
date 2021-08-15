package ir.dagger.quizak.bootstrap

import ir.dagger.quizak.db.entity.customers.Address
import ir.dagger.quizak.db.entity.customers.Institute
import ir.dagger.quizak.db.repostiory.*
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import ir.dagger.quizak.db.entity.Class

@Component
class LoadData(
    private val instituteRepository: InstituteRepository,
    private val classRepository: ClassRepository
) {
//    init {
//        var institute = instituteRepository.save(
//            Institute(
//                name = "Quizak",
//                address = Address(
//                    0, 0,
//                    "Birjand", "Khorasan Joonbi",
//                    "IIR", "somewhere"
//                ),
//                description = "We have quiz :(",
//                email = "contact@quizak.ir",
//                phone = "989151602459",
//                salt = "salt1",
//                uniqueName = "quizak_ins"
//            )
//        )
//        institute.addClass(
//            Class(
//                "default_cls", "text.default",
//                isPublicForAnyoneToUse = true,
//                isPublicForAnyoneAddQuiz = true,
//            )
//        )
//        institute = instituteRepository.save(institute)
//        defaultInstitute = institute.id
//        defaultClass = classRepository.findByUniqueName("default_cls")
//            .orElseThrow().id
//    }

//    @Bean
//    fun loader() = CommandLineRunner {
//        val institute1 =
//
//        instituteRepository.save(institute1)
//        println("Bean started")
//        val user1 = User(
//            "Ahrari",
//            LocalDate.parse("2000-02-01"),
//            "iahrari", "salt",
//            "989151612459","r.ahrari.i@gmail.com", "Iman"
//        )
//
//        val user2 = User(
//            "Geller",
//            LocalDate.parse("1980-02-01"),
//            "rGeller", "salt2",
//            "989151602459","ros.geller@gmail.com", "Ross"
//        )


//        userRepository.saveAll(listOf(user1, user2))

//        val users = userRepository.findAll()
//        val institutes = instituteRepository.findAll()
//
//        user2.apply {
//            addProfession("Palaeontology")
//            addProfession("Evolution")
//            println(getUserProfessions())
//            println("id: $id")
//            userRepository.save(this)
//        }
//
//        institute1.addHiredTeacher(HiredTeacher(user2, LocalDateTime.now(), LocalDateTime.now().plusMonths(12)))
//        instituteRepository.save(institute1)
//
//
//        institute1.addClass(c)
//        instituteRepository.save(institute1)
//        c = classRepository.findAll()[0]
//        var q = Quiz(c, "Quiz1")
//        c.addQuiz(q)
//        classRepository.save(c)
//
//        q = quizRepository.findAll()[0]
//        q.addQuestion(TrueFalseQ("Is it true?", 14000, false))
//        q = quizRepository.save(q)
//
//        val session = QuizSession(user1, BaseTimeBased(startedTime = LocalDateTime.now()), c)
//        q.addSession(session)
//        q = quizRepository.save(q)
//
//        q.getSessions().forEach {
//            println("user in session: $it")
//            it.addUser(UserInSession(user1, "iman"))
//            val s = sessionRepository.save(it)
//            s.getUsers().forEach { uis ->
//                q.getQuestions().forEach { q ->
//                    val l = LocalDateTime.now()
//                    uis.addAnswer(q.type.answerType.constructors[0].newInstance(q.id.row, l, l, false) as BaseAnswer)
//                }
//
//                userInSessionRepository.save(uis)
//            }
//        }
//
//        println(users)
//        println(institutes)
//    }

    @Bean
//    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
    fun defaultInstitute() =
        instituteRepository.findByUniqueName("quizak_ins").get()

    @Bean
//    @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
    fun defaultClass() =
        classRepository.findByUniqueName("default_cls").get()

    companion object {
        private var defaultClass: String? = null
        private var defaultInstitute: String? = null
    }
}

