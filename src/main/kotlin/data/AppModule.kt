package org.delcom.data

import org.delcom.controllers.CashFlowController
import org.delcom.repositories.CashFlowRepository
import org.delcom.services.CashFlowService

object AppModule {
    val cashFlowRepository = CashFlowRepository()
    val cashFlowService = CashFlowService(cashFlowRepository)
    val cashFlowController = CashFlowController(cashFlowService)
}
