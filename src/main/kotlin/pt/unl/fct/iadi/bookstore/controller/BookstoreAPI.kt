package pt.unl.fct.iadi.bookstore.controller

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import pt.unl.fct.iadi.bookstore.controller.dto.BookCreateRequest
import pt.unl.fct.iadi.bookstore.controller.dto.BookReplaceRequest
import pt.unl.fct.iadi.bookstore.controller.dto.BookResponse
import pt.unl.fct.iadi.bookstore.controller.dto.ErrorResponse
import pt.unl.fct.iadi.bookstore.controller.dto.PartialBookUpdate
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewCreateRequest
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewPartialUpdate
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewReplaceRequest
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewResponse

interface BookstoreAPI {

    // **** BOOKS ****

    // US1
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Books where listed"),
            ApiResponse(responseCode = "400", description = "Invalid book data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Could not find books",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @GetMapping("/books")
    fun listBooks(): List<BookResponse>

    // US2
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Book created"),
            ApiResponse(responseCode = "400", description = "Invalid book data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PostMapping("/books")
    fun createBook(@RequestBody book: BookCreateRequest): ResponseEntity<BookResponse>

    // US3
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Books was found"),
            ApiResponse(responseCode = "400", description = "Invalid book data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Could not find book",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @GetMapping("/books/{isbn}")
    fun getBook(@PathVariable isbn: String): BookResponse

    // US4
    @PutMapping("/books/{isbn}")
    fun replaceBook(@PathVariable isbn: String, @RequestBody book: BookReplaceRequest): BookResponse

    // US5
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Book updated"),
            ApiResponse(responseCode = "400", description = "Invalid book data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Could not find book",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PatchMapping("/books/{isbn}")
    fun partialUpdateBook(@PathVariable isbn: String, @RequestBody update: PartialBookUpdate): BookResponse

    // US6
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Book deleted"),
            ApiResponse(responseCode = "404", description = "Could not find book",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @DeleteMapping("/books/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable isbn: String)

    // **** REVIEWS ****

    // US7
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Reviews where listed"),
            ApiResponse(responseCode = "400", description = "Invalid book data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Could not find reviews",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @GetMapping("/books/{isbn}/reviews")
    fun listReviews(@PathVariable isbn: String): List<ReviewResponse>

    // US8
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Review created"),
            ApiResponse(responseCode = "400", description = "Invalid book data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Invalid review data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PostMapping("/books/{isbn}/reviews")
    fun createReview(@PathVariable isbn: String, @RequestBody review: ReviewCreateRequest): ResponseEntity<ReviewResponse>

    // US9
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Book replaced"),
            ApiResponse(responseCode = "400", description = "Invalid book data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Review not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PutMapping("/books/{isbn}/reviews/{id}")
    fun replaceReview(@PathVariable isbn: String, @PathVariable id: Long, @RequestBody review: ReviewReplaceRequest): ReviewResponse

    // US10
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Book updated"),
            ApiResponse(responseCode = "400", description = "Invalid book data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Could not find book",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @PatchMapping("/books/{isbn}/reviews/{id}")
    fun partialUpdateReview(@PathVariable isbn: String, @PathVariable id: Long, @RequestBody update: ReviewPartialUpdate): ReviewResponse

    // US11
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Review deleted"),
            ApiResponse(responseCode = "400", description = "Invalid review data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(responseCode = "404", description = "Could not find review",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @DeleteMapping("/books/{isbn}/reviews/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReview(@PathVariable isbn: String, @PathVariable id: Long)
}
