package pt.unl.fct.iadi.bookstore.controller.dto

import jakarta.validation.constraints.*
import org.hibernate.validator.constraints.URL
import java.math.BigDecimal
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request body for creating a new book")
data class BookCreateRequest(

    @Schema(description = "Unique ISBN of the book (non-blank)")
    @field:NotBlank(message = "ISBN cannot be blank")
    val isbn: String,

    @Schema(description = "Book title (1-120 characters)")
    @field:NotBlank(message = "Title cannot be blank")
    @field:Size(min = 1, max = 120, message = "Title must be at most 120 characters")
    val title: String,

    @Schema(description = "Book author (1-80 characters)")
    @field:NotBlank(message = "Author cannot be blank")
    @field:Size(min = 1, max = 80, message = "Author must be at most 80 characters")
    val author: String,

    @Schema(description = "Book price (must be greater than 0)")
    @field:NotNull(message = "Price cannot be null")
    @field:Positive
    @field:DecimalMin(value = "0.01", message = "Price must be greater than 0")
    val price: BigDecimal,

    @Schema(description = "URL to book cover image")
    @field:NotBlank(message = "Image URL cannot be blank")
    @field:URL(message = "Image must be a valid URL")
    val image: String
)