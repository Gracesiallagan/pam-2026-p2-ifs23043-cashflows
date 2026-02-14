package org.delcom.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import org.delcom.data.CashFlowQuery
import org.delcom.data.DataResponse
import org.delcom.helpers.loadInitialData
import org.delcom.services.ICashFlowService

class CashFlowController(
    private val cashFlowService: ICashFlowService
) {

    suspend fun setupData(call: ApplicationCall) {
        val query = CashFlowQuery()
        val cashFlows = cashFlowService.getAllCashFlows(query)

        for (cashFlow in cashFlows) {
            cashFlowService.removeCashFlow(cashFlow.id)
        }

        val initCashFlows = loadInitialData()

        for (cashFlow in initCashFlows) {
            cashFlowService.createRawCashFlow(
                cashFlow.id,
                cashFlow.type,
                cashFlow.source,
                cashFlow.label,
                cashFlow.amount,
                cashFlow.description,
                cashFlow.createdAt,
                cashFlow.updatedAt
            )
        }

        call.respond(
            DataResponse(
                "success",
                "Berhasil memuat data awal",
                null
            )
        )
    }
}
