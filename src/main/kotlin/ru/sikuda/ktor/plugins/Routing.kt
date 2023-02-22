package ru.sikuda.ktor.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import ru.sikuda.ktor.routes.*

fun Application.configureRouting() {
    routing {
        customTaskRouting()
        get("/") {
            call.respondText("Hello OTUS workwork!")
        }
    }
}


