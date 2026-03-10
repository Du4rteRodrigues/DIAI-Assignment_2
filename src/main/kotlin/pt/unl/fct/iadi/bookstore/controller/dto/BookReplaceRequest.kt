package pt.unl.fct.iadi.bookstore.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL
import java.math.BigDecimal

@Schema(description = "Image ID")
data class BookReplaceRequest(
    @Schema(description = "Image ID")
    @field:NotBlank
    val isbn: String,

    @Schema(description = "Book Name")
    @field:NotBlank
    @field:Size(min = 1,max = 120)
    val title: String,

    @Schema(description = "Author Name")
    @field:NotBlank
    @field:Size(min = 1, max = 80)
    val author: String,

    @Schema(description = "Price")
    @field:NotNull
    @field:DecimalMin("0.01")
    @field:Positive
    val price: BigDecimal,

    @Schema(description = "Image Url")
    @field:NotBlank
    @field:URL
    val image: String
)
