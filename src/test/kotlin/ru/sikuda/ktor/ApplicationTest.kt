package ru.sikuda.ktor

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import ru.sikuda.ktor.models.CustomTask
import ru.sikuda.ktor.models.dateTest
import ru.sikuda.ktor.plugins.configureRouting
import kotlin.test.Test
import kotlin.test.assertEquals


class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello OTUS project work!", bodyAsText())
        }
    }
    @Test
    fun testTasks() = testApplication {
        application {
            configureRouting()
        }
        val client2 = createClient {
            this@testApplication.install(ContentNegotiation) {
                json()
            }
        }

//        client.apply {
//            install(ContentNegotiation) {
//                json()
//            }
//        }
        //list task
        client.get("/customTasks").apply {
            assertEquals(HttpStatusCode.OK, status)
            val element = Json.parseToJsonElement(bodyAsText())
            assertEquals((element.jsonObject).size, 4)
        }

        //add task
        val response2 = client2.post("/customTasks") {
            contentType(ContentType.Application.Json)
            //setBody(CustomTask( dateTest(), "Comment for test task", mapOf()))
            setBody(Json.encodeToString(CustomTask( dateTest(), "Comment for test task", mapOf())))
            //setBody("{\"date\":\"2023-02-24\", \"comment\":\"one task\", \"mapPhotos\":{} }")
        }
        assertEquals("Customer stored correctly", response2.bodyAsText())
        assertEquals(HttpStatusCode.Created, response2.status)

        //list task
        var idtest = ""
        client.get("/customTasks").apply {
            assertEquals(HttpStatusCode.OK, status)
            val element = Json.parseToJsonElement(bodyAsText())
            assertEquals((element.jsonObject).size, 5)

            idtest = (element.jsonObject).toList()[0].first
        }

        //delete
//        client.delete("/customTask/?id=$idtest"){
//
//        }.apply {
//            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Custom Task removed correctly", bodyAsText())
//        }
    }
}
