package org.delcom.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.delcom.data.*
import org.delcom.entities.CashFlow
import org.delcom.helpers.ValidatorHelper
import org.delcom.helpers.loadInitialData
import org.delcom.services.ICashFlowService
import java.time.Instant
import java.util.UUID

class CashFlowController(
    private val cashFlowService: ICashFlowService
) {

    // =============================
    // SETUP DATA AWAL
    // =============================
    suspend fun setupData(call: ApplicationCall) {

        val existing = cashFlowService.getAllCashFlows(CashFlowQuery())

        for (cashFlow in existing) {
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

    // =============================
    // GET ALL + FILTER
    // =============================
    suspend fun getAll(call: ApplicationCall) {

        val query = CashFlowQuery(
            type = call.request.queryParameters["type"],
            source = call.request.queryParameters["source"],
            labels = call.request.queryParameters["labels"],
            search = call.request.queryParameters["search"],
            gteAmount = call.request.queryParameters["gteAmount"]?.toIntOrNull(),
            lteAmount = call.request.queryParameters["lteAmount"]?.toIntOrNull(),
            startDate = call.request.queryParameters["startDate"],
            endDate = call.request.queryParameters["endDate"]
        )

        val result = cashFlowService.getAllCashFlows(query)

        call.respond(
            DataResponse(
                "success",
                "Berhasil mengambil data",
                result
            )
        )
    }

    // =============================
    // GET BY ID
    // =============================
    suspend fun getById(call: ApplicationCall) {

        val id = call.parameters["id"]
            ?: throw AppException("Id tidak ditemukan")

        val result = cashFlowService.getCashFlowById(id)
            ?: throw AppException("Data tidak ditemukan")

        call.respond(
            DataResponse(
                "success",
                "Berhasil mengambil data",
                result
            )
        )
    }

    // =============================
    // CREATE
    // =============================
    suspend fun create(call: ApplicationCall) {

        val body = call.receive<CashFlowRequest>()

        // ✅ VALIDATION
        ValidatorHelper.validate(body)

        val id = UUID.randomUUID().toString()
        val now = Instant.now().toString()

        cashFlowService.createRawCashFlow(
            id,
            body.type,
            body.source,
            body.label,
            body.amount.toInt(),
            body.description,
            now,
            now
        )

        call.respond(
            DataResponse(
                "success",
                "Berhasil menambahkan data",
                mapOf("cashFlowId" to id)
            )
        )
    }

    // =============================
    // UPDATE
    // =============================
    suspend fun update(call: ApplicationCall) {

        val id = call.parameters["id"]
            ?: throw AppException("Id tidak ditemukan")

        val body = call.receive<CashFlowRequest>()

        // ✅ VALIDATION
        ValidatorHelper.validate(body)

        val existing = cashFlowService.getCashFlowById(id)
            ?: throw AppException("Data tidak ditemukan")

        val now = Instant.now().toString()

        val updated = existing.copy(
            type = body.type,
            source = body.source,
            label = body.label,
            amount = body.amount.toInt(),
            description = body.description,
            updatedAt = now
        )

        cashFlowService.updateCashFlow(id, updated)

        call.respond(
            DataResponse(
                "success",
                "Berhasil mengubah data",
                null
            )
        )
    }

    // =============================
    // DELETE
    // =============================
    suspend fun delete(call: ApplicationCall) {

        val id = call.parameters["id"]
            ?: throw AppException("Id tidak ditemukan")

        val existing = cashFlowService.getCashFlowById(id)
            ?: throw AppException("Data tidak ditemukan")

        cashFlowService.removeCashFlow(id)

        call.respond(
            DataResponse(
                "success",
                "Berhasil menghapus data",
                null
            )
        )
    }

    // =============================
    // EXTENDS
    // =============================
    suspend fun getTypes(call: ApplicationCall) {
        val data = cashFlowService.getAllCashFlows(CashFlowQuery())
            .map { it.type }
            .distinct()
            .sorted()

        call.respond(DataResponse("success", "List types", data))
    }

    suspend fun getSources(call: ApplicationCall) {
        val data = cashFlowService.getAllCashFlows(CashFlowQuery())
            .map { it.source }
            .distinct()
            .sorted()

        call.respond(DataResponse("success", "List sources", data))
    }

    suspend fun getLabels(call: ApplicationCall) {
        val data = cashFlowService.getAllCashFlows(CashFlowQuery())
            .flatMap { it.label.split(",") }
            .map { it.trim() }
            .distinct()
            .sorted()

        call.respond(DataResponse("success", "List labels", data))
    }
}
