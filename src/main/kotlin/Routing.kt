package org.delcom

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.delcom.data.AppModule

fun Application.configureRouting() {

    routing {

        // Root (optional)
        get("/") {
            call.respondText("Cash Flow API is running")
        }

        route("/cash-flows") {

            // =============================
            // SETUP DATA (HARUS DI SINI)
            // =============================
            post("/setup") {
                AppModule.cashFlowController.setupData(call)
            }

            // =============================
            // CRUD
            // =============================

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

            // update data
            put("/{id}") {
                AppModule.cashFlowController.update(call)
            }

            // hapus data
            delete("/{id}") {
                AppModule.cashFlowController.delete(call)
            }

            // =============================
            // EXTENDS
            // =============================
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
