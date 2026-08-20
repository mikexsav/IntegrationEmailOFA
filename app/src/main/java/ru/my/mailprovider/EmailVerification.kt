package ru.my.mailprovider

import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.random.Random

object EmailVerification {

    private const val SMTP_HOST = "smtp.spaceweb.ru"
    private const val SMTP_PORT = "465"

    private const val SENDER_EMAIL = "EMAIL"
    private const val SENDER_PASSWORD = ""

    fun generateCode(): String {
        return Random.nextInt(0, 1000000)
            .toString()
            .padStart(6, '0')
    }

    fun sendCode(
        email: String,
        code: String
    ) {
        val properties = Properties().apply {
            put("mail.smtp.host", SMTP_HOST)
            put("mail.smtp.port", SMTP_PORT)
            put("mail.smtp.auth", "true")
            put("mail.smtp.ssl.enable", "true")
        }

        val session = Session.getInstance(
            properties,
            object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(
                        SENDER_EMAIL,
                        SENDER_PASSWORD
                    )
                }
            }
        )

        val message = MimeMessage(session)

        message.setFrom(
            InternetAddress(
                SENDER_EMAIL,
                "Crewloom"
            )
        )

        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(email)
        )

        message.subject = "$code — код подтверждения Crewloom"

        message.setContent(
            createHtml(code),
            "text/html; charset=utf-8"
        )

        Transport.send(message)
    }

    private fun createHtml(code: String): String {
        return """
            
        """.trimIndent()
    }
}