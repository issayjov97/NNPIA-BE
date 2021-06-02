package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.TransactionResponseApi
import cz.upce.nnpia.skills.api.toTransaction
import cz.upce.nnpia.skills.persistence.*
import cz.upce.nnpia.skills.service.NotificationService
import cz.upce.nnpia.skills.service.TransactiontService
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
        private val userRepository: UserRepository,
        private val postRepository: PostRepository,
        private val transactionRepository: TransactionRepository,
        private val notificationService: NotificationService

) : TransactiontService {
    override fun createTransaction(transactionResponseApi: TransactionResponseApi) {
        val userEntity = userRepository.findByEmail(transactionResponseApi.user?.username!!)
        val postEntity = postRepository.getOne(transactionResponseApi.post?.id!!);
        transactionRepository.saveAndFlush(
                TransactionsEntity(
                        requestedAt = transactionResponseApi.requestedAt!!,
                        post = postEntity,
                        user = userEntity!!,
                        status = transactionResponseApi.status!!
                )
        )
    }

    override fun getTransactions(username: String, status: TransactionState?): List<TransactionResponseApi> = transactionRepository.findTransactions(
            username, status
    ).map {
        it.toTransaction()
    }

    override fun updateTransaction(transactionResponseApi: TransactionResponseApi) {
        val transactionEntity = transactionRepository.getOne(transactionResponseApi.id!!)
        transactionResponseApi.agreedSkillHours?.let {
            transactionEntity.agreedSkillHours = it
            notificationService.sendEmail("Agreed hours: $it", transactionEntity.user.email)
        }
        transactionResponseApi.approvedByAuthorAt?.let {
            transactionEntity.approvedByAuthorAt = it
            notificationService.sendEmail("Request was approved by author at $it", transactionEntity.user.email)
        }
        transactionResponseApi.approvedByRequesterAt?.let {
            transactionEntity.approvedByRequesterAt = it;
            notificationService.sendEmail("Request was approved by requester at $it", transactionEntity.post.user.email)
        }
        transactionResponseApi.deliveredByAuthorAt?.let {
            transactionEntity.deliveredByAuthorAt = it;
            notificationService.sendEmail("Skill has been set as delivered at $it", transactionEntity.user.email)
        }
        transactionResponseApi.deliveredByRequesterAt?.let {
            transactionEntity.deliveredByRequesterAt = it;
            notificationService.sendEmail("Skill has been confirmed as delivered at $it", transactionEntity.post.user.email)

        }
        transactionResponseApi.status?.let {
            transactionEntity.status = it;
        }
        transactionRepository.saveAndFlush(transactionEntity)
    }
}


