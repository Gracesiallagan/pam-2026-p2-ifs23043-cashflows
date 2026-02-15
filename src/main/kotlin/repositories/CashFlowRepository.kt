package org.delcom.repositories

import org.delcom.data.CashFlowQuery
import org.delcom.entities.CashFlow

class CashFlowRepository : ICashFlowRepository {

    private val cashFlows = mutableListOf<CashFlow>()

    override fun getAll(query: CashFlowQuery): List<CashFlow> {
        return cashFlows.filter {

            (query.type == null || it.type.equals(query.type, true)) &&

                    (query.source == null || it.source.equals(query.source, true)) &&

                    (query.search == null || it.description.contains(query.search, true)) &&

                    (query.gteAmount == null || it.amount >= query.gteAmount) &&

                    (query.lteAmount == null || it.amount <= query.lteAmount) &&
                    (query.startDate == null || it.createdAt >= query.startDate) &&

                    (query.endDate == null || it.createdAt <= query.endDate) &&

                    (query.labels == null ||
                            query.labels.split(",").any { qLabel ->
                                it.label.split(",")
                                    .map { dataLabel -> dataLabel.trim() }
                                    .any { dataLabel ->
                                        dataLabel.equals(qLabel.trim(), true)
                                    }
                            }
                            )
        }
    }


    override fun getById(id: String): CashFlow? =
        cashFlows.find { it.id == id }

    override fun create(cashFlow: CashFlow) {
        cashFlows.add(cashFlow)
    }

    override fun delete(id: String) {
        cashFlows.removeIf { it.id == id }
    }

    override fun clear() {
        cashFlows.clear()
    }
    override fun update(id: String, cashFlow: CashFlow) {
        val index = cashFlows.indexOfFirst { it.id == id }
        if (index != -1) {
            cashFlows[index] = cashFlow
        }
    }
}
