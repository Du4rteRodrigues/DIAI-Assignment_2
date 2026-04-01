package pt.unl.fct.iadi.bookstore.controller

import io.swagger.v3.oas.annotations.headers.Header
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
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

    // US1 - list books
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Books were listed"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid query parameters",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "No books found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books"],
        method = [RequestMethod.GET]
    )
    fun listBooks(): List<BookResponse>

    // US2 - create book
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "Book created",
            headers = [Header(
                name = "Location",
                description = "URI of the created book",
                schema = Schema(type = "string", format = "uri")
            )],
            content = [Content(schema = Schema(hidden = true))]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Validation error",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Book with this ISBN already exists",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]
        )
    )
    @RequestMapping(
        value = ["/books"],
        consumes = ["application/json"],
        method = [RequestMethod.POST]
    )
    fun createBook(@RequestBody book: BookCreateRequest): ResponseEntity<BookResponse>

    // US3 - get single book
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Book was found"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid ISBN format",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}"],
        method = [RequestMethod.GET]
    )
    fun getBook(@PathVariable isbn: String): BookResponse

    // US4 - replace book
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Book replaced"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Validation error",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}"],
        consumes = ["application/json"],
        method = [RequestMethod.PUT]
    )
    fun replaceBook(
        @PathVariable isbn: String,
        @RequestBody book: BookReplaceRequest
    ): BookResponse

    // US5 - partial update book
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Book updated"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Validation error",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}"],
        consumes = ["application/json"],
        method = [RequestMethod.PATCH]
    )
    fun partialUpdateBook(
        @PathVariable isbn: String,
        @RequestBody update: PartialBookUpdate
    ): BookResponse

    // US6 - delete book
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Book deleted"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}"],
        method = [RequestMethod.DELETE]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable isbn: String)

    // **** REVIEWS ****

    // US7 - list reviews
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Reviews were listed"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Book not found or no reviews",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}/reviews"],
        method = [RequestMethod.GET]
    )
    fun listReviews(@PathVariable isbn: String): List<ReviewResponse>

    // US8 - create review
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Review created"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid review data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Book not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}/reviews"],
        consumes = ["application/json"],
        method = [RequestMethod.POST]
    )
    fun createReview(
        @PathVariable isbn: String,
        @RequestBody review: ReviewCreateRequest
    ): ResponseEntity<ReviewResponse>

    // US9 - replace review
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Review replaced"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid review data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Review or book not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}/reviews/{id}"],
        consumes = ["application/json"],
        method = [RequestMethod.PUT]
    )
    fun replaceReview(
        @PathVariable isbn: String,
        @PathVariable id: Long,
        @RequestBody review: ReviewReplaceRequest
    ): ReviewResponse

    // US10 - partial update review
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Review updated"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid review data",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Review or book not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}/reviews/{id}"],
        consumes = ["application/json"],
        method = [RequestMethod.PATCH]
    )
    fun partialUpdateReview(
        @PathVariable isbn: String,
        @PathVariable id: Long,
        @RequestBody update: ReviewPartialUpdate
    ): ReviewResponse

    // US11 - delete review
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Review deleted"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid review identifier",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Review or book not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}/reviews/{id}"],
        method = [RequestMethod.DELETE]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReview(
        @PathVariable isbn: String,
        @PathVariable id: Long
    )
}
