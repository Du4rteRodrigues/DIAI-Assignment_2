package pt.unl.fct.iadi.bookstore.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

data class ReviewCreateRequest(

    @Schema(description = "Rating of review")
    @field:Min(1)
    @field:Max(5)
    val rating: Int,

    @Schema(description = "Comment for review")
    @field:Size(min = 1, max = 500)
    val comment: String?
)
