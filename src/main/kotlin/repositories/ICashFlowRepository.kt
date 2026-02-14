package org.delcom.repositories

import org.delcom.data.CashFlowQuery
import org.delcom.entities.CashFlow

interface ICashFlowRepository {
    fun getAll(query: CashFlowQuery): List<CashFlow>
    fun getById(id: String): CashFlow?
    fun create(cashFlow: CashFlow)
    fun delete(id: String)
    fun clear()
}
