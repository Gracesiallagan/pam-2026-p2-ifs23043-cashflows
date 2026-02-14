package org.delcom

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.delcom.data.AppModule

fun Application.configureRouting() {
    routing {
        route("/cash-flows") {
            post("/setup") {
                AppModule.cashFlowController.setupData(call)
            }
        }
    }
}
