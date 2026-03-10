package pt.unl.fct.iadi.bookstore.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Book resource representation")
data class BookResponse(

    @Schema(description = "Unique ISBN of the book")
    val isbn: String,

    @Schema(description = "Book title")
    val title: String,

    @Schema(description = "Book author")
    val author: String,

    @Schema(description = "Book price")
    val price: BigDecimal,

    @Schema(description = "URL to book cover image")
    val image: String
)