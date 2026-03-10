package pt.unl.fct.iadi.bookstore.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Review resource representation")
data class ReviewResponse(

    @Schema(description = "Unique identifier of the review")
    val id: Long,

    @Schema(description = "Rating given to the book (1-5)")
    val rating: Int,

    @Schema(description = "Optional comment about the book")
    val comment: String?
)