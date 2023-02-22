package ru.sikuda.ktor.models

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CustomTask(val id: String, val date: String, val comment: String)

val customTaskStorage = mutableListOf<CustomTask>(
    CustomTask("1","2023-02-22", "one"),
    CustomTask("2","2023-02-23", "two"),
    CustomTask("3","2023-02-24", "three"),
    CustomTask("4","2023-02-25", "four")
)


