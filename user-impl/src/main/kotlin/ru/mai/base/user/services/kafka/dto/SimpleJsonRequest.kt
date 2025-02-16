package ru.mai.base.user.services.kafka.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SimpleJsonRequest(
    @JsonProperty("application_name") val name: String,
    @JsonProperty("some_id") val id: Long,
) {
}
