package cz.upce.nnpia.skills.controller

import cz.upce.nnpia.skills.api.TransactionResponseApi
import cz.upce.nnpia.skills.persistence.TransactionState
import cz.upce.nnpia.skills.service.TransactiontService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@CrossOrigin
@RestController
@RequestMapping("/api/v1/transactions")
class TransactionController(
        private val transactionService: TransactiontService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestBody request: TransactionResponseApi) = transactionService.createTransaction(request)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getTransactions(
            @RequestParam username: String,
            @RequestParam status: TransactionState?
    ) = transactionService.getTransactions(username, status)

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateTransaction(@RequestBody transactionResponseApi: TransactionResponseApi) = transactionService.updateTransaction(transactionResponseApi)
}