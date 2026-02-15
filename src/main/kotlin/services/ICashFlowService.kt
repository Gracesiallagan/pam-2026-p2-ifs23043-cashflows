package org.delcom.services

import org.delcom.data.CashFlowQuery
import org.delcom.entities.CashFlow

interface ICashFlowService {
    fun getAllCashFlows(query: CashFlowQuery): List<CashFlow>
    fun getCashFlowById(id: String): CashFlow?
    fun createRawCashFlow(
        id: String,
        type: String,
        source: String,
        label: String,
        amount: Int,
        description: String,
        createdAt: String,
        updatedAt: String
    )
    fun removeCashFlow(id: String)
    fun updateCashFlow(id: String, cashFlow: CashFlow)
}

