package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.service.NotificationService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine

@Service
class NotificationServiceImpl(
        private val javaMailSender: JavaMailSender,
        private val templateEngine: SpringTemplateEngine
) : NotificationService {
    companion object {
        private val LOGGER: Logger = LogManager.getLogger(NotificationServiceImpl::class)
    }

    @Async
    override fun sendEmail(content: String, to: String) {
        try {
            val mimeMessage = javaMailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, true)
            val context = Context()
            context.setVariable("name", to)
            context.setVariable("content", content)
            helper.setSubject("Skills app mail notifier")
            helper.setTo(to)
            helper.setText(templateEngine.process("notification", context), true)
            javaMailSender.send(mimeMessage)
        } catch (ex: MailException) {
            LOGGER.error("Failed to send email to {}", to, ex)
        }
    }
}
