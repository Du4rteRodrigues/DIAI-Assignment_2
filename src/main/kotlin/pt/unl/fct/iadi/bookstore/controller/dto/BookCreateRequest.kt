package pt.unl.fct.iadi.bookstore.controller.dto

import jakarta.validation.constraints.*
import java.math.BigDecimal
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request body for creating a new book")
data class BookCreateRequest(

    @Schema(description = "Unique ISBN of the book (non-blank)", example = "9780134685991")
    @NotBlank(message = "ISBN cannot be blank")
    val isbn: String,

    @Schema(description = "Book title (1-120 characters)", example = "Clean Code")
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 1, max = 120, message = "Title must be between 1-120 characters")
    val title: String,

    @Schema(description = "Book author (1-80 characters)", example = "Robert C. Martin")
    @NotBlank(message = "Author cannot be blank")
    @Size(min = 1, max = 80, message = "Author must be between 1-80 characters")
    val author: String,

    @Schema(description = "Book price (must be greater than 0)", example = "29.99")
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    val price: BigDecimal,

    @Schema(description = "URL to book cover image (must be valid URL)", example = "https://example.com/book-cover.jpg")
    @NotBlank(message = "Image URL cannot be blank")
    @Url(message = "Image must be a valid URL")
    val image: String
)
