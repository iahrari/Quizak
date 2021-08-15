package ir.dagger.quizak

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.security.MessageDigest
import java.time.ZoneId
import java.time.ZonedDateTime

@SpringBootTest
class QuizakApplicationTests {

    @Test
    fun contextLoads() {
//        val l = ZonedDateTime.now()
        val z = ZonedDateTime.parse("2017-11-15T08:22:12+01:00[Europe/Paris]")
        val d = z.withZoneSameInstant(ZoneId.of("UTC"))

        println("Zone time: $d")
    }

    @Test
    fun checkHashSize(){
        val message = MessageDigest.getInstance("SHA-256")
        val text = "Hello"
        val array = message.digest(text.encodeToByteArray())

        println("array: ${array.fold("") { str, it -> str + "%02x".format(it) }.length}")
    }


}
