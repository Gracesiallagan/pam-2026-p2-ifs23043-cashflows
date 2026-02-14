package org.delcom.services

import org.delcom.data.CashFlowQuery
import org.delcom.entities.CashFlow
import org.delcom.repositories.ICashFlowRepository

class CashFlowService(
    private val repository: ICashFlowRepository
) : ICashFlowService {

    override fun getAllCashFlows(query: CashFlowQuery): List<CashFlow> =
        repository.getAll(query)

    override fun getCashFlowById(id: String): CashFlow? =
        repository.getById(id)

    override fun createRawCashFlow(
        id: String,
        type: String,
        source: String,
        label: String,
        amount: Int,
        description: String,
        createdAt: String,
        updatedAt: String
    ) {
        repository.create(
            CashFlow(
                id, type, source, label, amount,
                description, createdAt, updatedAt
            )
        )
    }

    override fun removeCashFlow(id: String) {
        repository.delete(id)
    }
}
