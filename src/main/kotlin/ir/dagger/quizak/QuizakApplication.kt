package ir.dagger.quizak

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuizakApplication

fun main(args: Array<String>) {
    runApplication<QuizakApplication>(*args)
}
