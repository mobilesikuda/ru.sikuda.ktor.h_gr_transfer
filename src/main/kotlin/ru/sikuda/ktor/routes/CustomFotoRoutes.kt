package ru.sikuda.ktor.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.sikuda.ktor.models.CustomPhoto
import ru.sikuda.ktor.models.customTaskStorage
import java.util.*

fun Route.customFotoRouting() {
    route("/customFotos") {
        //get list fotos by id task
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                customTaskStorage.get(id) ?: return@get call.respondText(
                    "No custom task with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer.mapPhotos)
        }
        //add photo to id task
        post("{id?}") {
            val id = call.parameters["id"] ?: return@post call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customFoto = call.receive<CustomPhoto>()
            customTaskStorage[id].also { task ->
                val mapPhotos = task?.mapPhotos?.toMutableMap() ?: mutableMapOf()
                mapPhotos[UUID.randomUUID().toString()] = customFoto
                task?.mapPhotos = mapPhotos.toMap()
            }
        }
        delete("{idtask?\\id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val idtask = call.parameters["idtask"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            customTaskStorage[idtask].also { task ->
                val mapPhotos = task?.mapPhotos?.toMutableMap() ?: mutableMapOf()
                mapPhotos.remove(id)
                task?.mapPhotos = mapPhotos.toMap()
            }

        }
    }
}