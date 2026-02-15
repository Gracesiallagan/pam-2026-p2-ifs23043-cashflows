package org.delcom

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import org.delcom.controllers.CashFlowController
import org.delcom.data.DataResponse
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val controller by inject<CashFlowController>()

    routing {
        get("/") {
            val response = DataResponse<String?>(
                status = "success",
                message = "11S23043-Grace Evelin Siallagan-Cashflows",
                data = null
            )
            call.respond(response)
        }

        route("/cash-flows") {
            post("/setup") { controller.setupData(call) }
            get { controller.getAll(call) }
            post { controller.create(call) }

            get("/types") { controller.getTypes(call) }
            get("/sources") { controller.getSources(call) }
            get("/labels") { controller.getLabels(call) }

            get("/{id}") { controller.getById(call) }
            put("/{id}") { controller.update(call) }
            delete("/{id}") { controller.delete(call) }
        }
    }
}