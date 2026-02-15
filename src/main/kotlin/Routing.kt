package org.delcom

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.delcom.data.AppModule

fun Application.configureRouting() {
    routing {

        route("/cash-flows") {

            // setup data awal
            post("/setup") {
                AppModule.cashFlowController.setupData(call)
            }

            // ambil semua data
            get {
                AppModule.cashFlowController.getAll(call)
            }

            // ambil berdasarkan id
            get("/{id}") {
                AppModule.cashFlowController.getById(call)
            }

            // tambah data
            post {
                AppModule.cashFlowController.create(call)
            }

            // update
            put("/{id}") {
                AppModule.cashFlowController.update(call)
            }

            // delete
            delete("/{id}") {
                AppModule.cashFlowController.delete(call)
            }
            get("/types") {
                AppModule.cashFlowController.getTypes(call)
            }

            get("/sources") {
                AppModule.cashFlowController.getSources(call)
            }

            get("/labels") {
                AppModule.cashFlowController.getLabels(call)
            }
        }
    }
}
