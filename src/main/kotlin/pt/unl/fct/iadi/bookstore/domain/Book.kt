package pt.unl.fct.iadi.bookstore.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class Book(
    @NotBlank
    val isbn: String,
    @Size(min = 1, max = 120, message = "Name must be between 1 and 120 characters")
    val title: String,
    @Size(min = 1, max = 80, message = "Author name must be between 1 and 80 characters")
    val author: String,
    @NotBlank
    val price: BigDecimal,
    val image: Any,
)
