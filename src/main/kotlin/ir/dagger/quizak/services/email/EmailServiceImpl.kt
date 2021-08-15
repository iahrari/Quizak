package ir.dagger.quizak.services.email

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(
    private val emailSender: JavaMailSender
): EmailService {
    //TODO: Add html template for message
    override fun sendSimpleMail(
        to: String,
        subject: String,
        text: String, from:
        String) {
        val message = SimpleMailMessage().apply {
            setTo(to)
            setSubject(subject)
            setText(text)
        }

        emailSender.send(message)
    }
}