package ru.sikuda.ktor.models

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Serializable
data class CustomTask(
    val date: String,
    val comment: String,
    var mapPhotos: Map<String, CustomPhoto>
)

@Serializable
data class CustomPhoto(
    //val id: String,
    val idTask: String?,
    val data: String,
    val comment: String
)

val customTaskStorage = mutableMapOf<String, CustomTask>(
    Pair(UUID.randomUUID().toString(), CustomTask( dateTest(), "one task", mapOf())),
    Pair(UUID.randomUUID().toString(), CustomTask( dateTest(), "two task", mapOf())),
    Pair(UUID.randomUUID().toString(), CustomTask( dateTest(), "three task", mapOf())),
    Pair(UUID.randomUUID().toString(), CustomTask( dateTest(), "four task", mapOf()))
)

fun dateTest(): String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


