package org.delcom

import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.netty.EngineMain
import kotlinx.serialization.json.Json
import org.delcom.data.AppException
import org.delcom.data.ErrorResponse

fun main(args: Array<String>) {

    val dotenv = dotenv {
        directory = "."
        ignoreIfMissing = false
    }

    dotenv.entries().forEach {
        System.setProperty(it.key, it.value)
    }

    EngineMain.main(args)
}

fun Application.module() {

    // =========================
    // JSON SERIALIZATION
    // =========================
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = false
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    // =========================
    // ERROR HANDLER
    // =========================
    install(StatusPages) {

        exception<AppException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse("error", cause.message ?: "Error")
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse("error", "Internal Server Error")
            )
        }
    }

    // =========================
    // ROUTING
    // =========================
    configureRouting()
}
