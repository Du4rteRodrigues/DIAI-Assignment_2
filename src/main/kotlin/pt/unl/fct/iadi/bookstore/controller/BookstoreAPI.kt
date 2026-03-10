package pt.unl.fct.iadi.bookstore.controller

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
import pt.unl.fct.iadi.bookstore.controller.dto.PartialBookUpdate
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewCreateRequest
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewPartialUpdate
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewReplaceRequest
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewResponse
import pt.unl.fct.iadi.bookstore.domain.Book
import pt.unl.fct.iadi.bookstore.domain.Review

interface BookstoreAPI {

    // **** BOOKS ****

    // US1
    @GetMapping("/books")
    fun listBooks(): List<BookResponse>

    // US2
    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody book: BookCreateRequest): ResponseEntity<BookResponse>

    // US3
    @GetMapping("/books/{isbn}")
    fun getBook(@PathVariable isbn: String): BookResponse

    // US4
    @PutMapping("/books/{isbn}")
    fun replaceBook(@PathVariable isbn: String, @RequestBody book: BookReplaceRequest): BookResponse

    // US5
    @PatchMapping("/books/{isbn}")
    fun partialUpdateBook(@PathVariable isbn: String, @RequestBody update: PartialBookUpdate): BookResponse

    // US6
    @DeleteMapping("/books/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable isbn: String)

    // **** REVIEWS ****

    // US7
    @GetMapping("/books/{isbn}/reviews")
    fun listReviews(@PathVariable isbn: String): List<ReviewResponse>

    // US8
    @PostMapping("/books/{isbn}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    fun createReview(@PathVariable isbn: String, @RequestBody review: ReviewCreateRequest): ResponseEntity<ReviewResponse>

    // US9
    @PutMapping("/books/{isbn}/reviews/{id}")
    fun replaceReview(@PathVariable isbn: String, @PathVariable id: Long, @RequestBody review: ReviewReplaceRequest): ReviewResponse

    // US10
    @PatchMapping("/books/{isbn}/reviews/{id}")
    fun partialUpdateReview(@PathVariable isbn: String, @PathVariable id: Long, @RequestBody update: ReviewPartialUpdate): ReviewResponse

    // US11
    @DeleteMapping("/books/{isbn}/reviews/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReview(@PathVariable isbn: String, @PathVariable id: Long)
}
