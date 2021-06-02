package cz.upce.nnpia.skills.service

import cz.upce.nnpia.skills.api.TransactionResponseApi
import cz.upce.nnpia.skills.persistence.TransactionState

interface TransactiontService {
    fun createTransaction(transactionResponseApi: TransactionResponseApi)
    fun getTransactions(username: String, status: TransactionState?): List<TransactionResponseApi>
    fun updateTransaction(transactionResponseApi: TransactionResponseApi)
}