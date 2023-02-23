package ru.sikuda.ktor.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.sikuda.ktor.models.CustomTask
import ru.sikuda.ktor.models.customTaskStorage
import java.util.*

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
                customTaskStorage[id] ?: return@get call.respondText(
                    "No custom task with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        //add by post
        post {
            //silence is gold
            //try {
            val customer = call.receive<CustomTask>()
            customTaskStorage.put(UUID.randomUUID().toString(), customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
            //} catch (e: BadRequestException) {
            //    call.respondText("Bad request", status = HttpStatusCode.BadRequest)
            //}
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (customTaskStorage.containsKey(id)) {
                customTaskStorage.remove(id)
                call.respondText("Custom Task removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }

    }
}