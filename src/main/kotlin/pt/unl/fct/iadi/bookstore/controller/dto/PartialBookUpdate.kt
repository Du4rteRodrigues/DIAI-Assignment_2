package pt.unl.fct.iadi.bookstore.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL
import java.math.BigDecimal

data class PartialBookUpdate(

    @Schema(description = "Book Name")
    @field:Size(min = 1, max = 120)
    val title: String?,

    @Schema(description = "Author Name")
    @field:Size(min = 1, max = 80)
    val author: String?,

    @Schema(description = "Book Price")
    @field:Positive
    val price: BigDecimal?,

    @Schema(description = "Image URL ")
    @field:URL
    val image: String?
)
