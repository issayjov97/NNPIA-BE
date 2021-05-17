package cz.upce.nnpia.skills.service.impl

import cz.upce.nnpia.skills.api.TransactionResponseApi
import cz.upce.nnpia.skills.api.toTransaction
import cz.upce.nnpia.skills.persistence.*
import cz.upce.nnpia.skills.service.TransactiontService
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
        private val userRepository: UserRepository,
        private val postRepository: PostRepository,
        private val transactionRepository: TransactionRepository

) : TransactiontService {
    override fun createTransaction(transactionResponseApi: TransactionResponseApi) {
        val userEntity = userRepository.findByEmail(transactionResponseApi.user.username);
        val postEntity = postRepository.getOne(transactionResponseApi.post.id!!);
        transactionRepository.saveAndFlush(
                TransactionsEntity(
                        requestedAt = transactionResponseApi.requestedAt,
                        post = postEntity,
                        user = userEntity!!,
                        status = transactionResponseApi.status
                )
        )
    }

    override fun getTransactions(username: String, status: TransactionState?): List<TransactionResponseApi> = transactionRepository.findTransactions(
            username, status
    ).map {
        it.toTransaction()
    }

    override fun getDeliveredSkills(username: String): List<TransactionResponseApi> {
        TODO("Not yet implemented")
    }

    override fun getReceivedSkills(username: String): List<TransactionResponseApi> {
        TODO("Not yet implemented")
    }

    override fun updateTransaction(transactionResponseApi: TransactionResponseApi) {
        val transactionEntity = transactionRepository.getOne(transactionResponseApi.id!!)
        transactionResponseApi.agreedSkillHours?.let {
            transactionEntity.agreedSkillHours = it;
        }
        transactionResponseApi.agreedSkillHours?.let {
            transactionEntity.agreedSkillHours = it;
        }
        transactionResponseApi.approvedByAuthorAt?.let {
            transactionEntity.approvedByAuthorAt = it;
        }
        transactionResponseApi.approvedByRequesterAt?.let {
            transactionEntity.approvedByRequesterAt = it;
        }
        transactionResponseApi.deliveredByAuthorAt?.let {
            transactionEntity.deliveredByAuthorAt = it;
        }
        transactionResponseApi.deliveredByRequesterAt?.let {
            transactionEntity.deliveredByRequesterAt = it;
        }
        transactionResponseApi.status.let {
            transactionEntity.status = it;
        }
        transactionRepository.saveAndFlush(transactionEntity)

    }


}


