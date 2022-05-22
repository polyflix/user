package fr.polyflix.user.application.http.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

data class ApiError(
    val status: HttpStatus,
    val message: String,
    @JsonProperty("exceptionClass")
    val clazz: String,
)