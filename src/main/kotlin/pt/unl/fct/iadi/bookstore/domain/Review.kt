package pt.unl.fct.iadi.bookstore.domain

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class Review(
    val id: Long,
    @Min(1) @Max(5)
    val rating: Int,
    @Size(max = 500)
    val comment: String?
)
