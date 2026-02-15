package org.delcom.data

import org.delcom.controllers.CashFlowController
import org.delcom.repositories.CashFlowRepository
import org.delcom.repositories.ICashFlowRepository
import org.delcom.services.CashFlowService
import org.delcom.services.ICashFlowService
import org.koin.dsl.module

val cashFlowModule = module {

    single<ICashFlowRepository> { CashFlowRepository() }

    single { CashFlowService(get()) }

    single { CashFlowController(get()) }
}