package ru.sikuda.ktor

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.sikuda.ktor.plugins.*

fun main() {
    embeddedServer(Netty, port = 8081, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    //configureSecurity() --todo
    configureRouting()
    configureSerialization()
}
