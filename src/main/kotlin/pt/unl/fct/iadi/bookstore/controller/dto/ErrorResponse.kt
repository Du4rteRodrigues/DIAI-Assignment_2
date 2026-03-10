package pt.unl.fct.iadi.bookstore.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Standard API error response")
data class ErrorResponse(

    @Schema(description = "Machine-readable error identifier")
    val error: String,

    @Schema(description = "Human-readable description of the error")
    val message: String
)