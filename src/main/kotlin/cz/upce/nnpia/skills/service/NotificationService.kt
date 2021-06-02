package cz.upce.nnpia.skills.service

interface NotificationService {
    fun sendEmail(content: String, to: String)
}