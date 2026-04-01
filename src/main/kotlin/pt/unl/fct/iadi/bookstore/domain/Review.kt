package pt.unl.fct.iadi.bookstore.domain

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class Review(

    @Schema(description = "ID of the review")
    val id: Long,

    @Schema(description = "Rating of the review")
    @Min(1) @Max(5)
    val rating: Int,

    @Schema(description = "Comment of the review")
    @NotBlank
    @Size(min = 1,max = 500)
    val comment: String?,

    var author: String? = null
)
