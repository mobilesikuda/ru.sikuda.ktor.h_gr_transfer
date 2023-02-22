package ru.sikuda.ktor.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.sikuda.ktor.models.customTaskStorage

fun Route.customTaskRouting() {

    route("/customTasks") {
        get {
            if (customTaskStorage.isNotEmpty()) {
                call.respond(customTaskStorage)
            } else {
                call.respondText("No custom tasks found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                customTaskStorage.find { it.id == id } ?: return@get call.respondText(
                    "No custom task with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
    }
}