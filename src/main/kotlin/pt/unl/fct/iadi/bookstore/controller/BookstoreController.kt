package pt.unl.fct.iadi.bookstore.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
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
import pt.unl.fct.iadi.bookstore.service.BookstoreService

@RestController
class BookstoreController(
    private val service: BookstoreService
) : BookstoreAPI {

    //**** Books ****

    override fun listBooks(): List<BookResponse> {
        val books = service.listBooks()
        return books.map { convertBookToResponse(it) }
    }

    override fun createBook(@Valid book: BookCreateRequest): ResponseEntity<BookResponse> {
        val createdBook = service.createBook(convertCreateRequestToBook(book))
        val response = convertBookToResponse(createdBook)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{isbn}")
            .buildAndExpand(createdBook.isbn)
            .toUri()

        return ResponseEntity.created(location).body(response)
    }

    override fun getBook(isbn: String): BookResponse {
        val book = service.getBook(isbn)
        return convertBookToResponse(book)
    }

    override fun replaceBook(@Valid isbn: String, book: BookReplaceRequest): BookResponse {
        val replacement = convertReplaceRequestToBook(isbn, book)
        val replacedBook = service.replaceBook(isbn, replacement)
        return convertBookToResponse(replacedBook)
    }

    override fun partialUpdateBook(@Valid isbn: String, update: PartialBookUpdate): BookResponse {
        val updatedBook = service.partialUpdateBook(isbn, update
        )
        return convertBookToResponse(updatedBook)
    }

    override fun deleteBook(isbn: String) {
        service.deleteBook(isbn)
    }

    //**** REVIEWS ****

    override fun listReviews(isbn: String): List<ReviewResponse> {
        val reviews = service.listReviews(isbn)
        return reviews.map { convertReviewToResponse(it) }
    }

    override fun createReview(@Valid isbn: String, review: ReviewCreateRequest): ResponseEntity<ReviewResponse> {
        val createdReview = service.createReview(isbn, convertCreateRequestToReview(review))
        val response = convertReviewToResponse(createdReview)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdReview.id)
            .toUri()

        return ResponseEntity.created(location).body(response)
    }

    override fun replaceReview(@Valid isbn: String, id: Long, review: ReviewReplaceRequest): ReviewResponse {
        val replacement = convertReplaceRequestToReview(id, review)
        val replacedReview = service.replaceReview(isbn, id, replacement)
        return convertReviewToResponse(replacedReview)
    }

    override fun partialUpdateReview(@Valid isbn: String, id: Long, update: ReviewPartialUpdate): ReviewResponse {
        val updatedReview = service.partialUpdateReview(isbn, id,update)
        return convertReviewToResponse(updatedReview)
    }

    override fun deleteReview(isbn: String, id: Long) {
        service.deleteReview(isbn, id)
    }

    //**** Mapping functions ****

    private fun convertBookToResponse(book: Book): BookResponse =
        BookResponse(book.isbn, book.title, book.author, book.price, book.image as String)

    private fun convertReviewToResponse(review: Review): ReviewResponse =
        ReviewResponse(review.id, review.rating, review.comment
        )

    private fun convertCreateRequestToBook(request: BookCreateRequest): Book =
        Book(request.isbn, request.title, request.author, request.price, request.image
        )

    private fun convertReplaceRequestToBook(isbn: String, request: BookReplaceRequest): Book =
        Book(isbn, request.title, request.author, request.price, request.image
        )

    private fun convertCreateRequestToReview(request: ReviewCreateRequest): Review =
        Review(0, request.rating, request.comment
        )

    private fun convertReplaceRequestToReview(id: Long, request: ReviewReplaceRequest): Review =
        Review(id, request.rating, request.comment
        )
}
