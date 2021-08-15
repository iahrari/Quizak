package ir.dagger.quizak.services.email

interface EmailService {
    //TODO: Change from email address
    fun sendSimpleMail(
        to: String,
        subject: String,
        text: String,
        from: String = "r.ahrari.i@gmail.com")
}
