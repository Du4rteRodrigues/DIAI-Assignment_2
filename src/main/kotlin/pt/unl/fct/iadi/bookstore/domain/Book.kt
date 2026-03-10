package pt.unl.fct.iadi.bookstore.domain

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class Book(
    @Schema(description = "isbn of the book")
    @NotBlank
    val isbn: String,

    @Schema(description = "Title of the book")
    @Size(min = 1, max = 120, message = "Name must be between 1 and 120 characters")
    val title: String,

    @Schema(description = "Author of the book")
    @Size(min = 1, max = 80, message = "Author name must be between 1 and 80 characters")
    val author: String,

    @Schema(description = "Price of the book")
    @NotBlank
    val price: BigDecimal,

    @Schema(description = "Image URL of the book")
    @NotBlank
    val image: String
)
