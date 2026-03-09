package pt.unl.fct.iadi.bookstore.controller

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

    // Prefer changing BookstoreAPI to return ResponseEntity<BookResponse>
    override fun createBook(book: BookCreateRequest): ResponseEntity<BookResponse> {
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

    override fun replaceBook(isbn: String, book: BookReplaceRequest): BookResponse {
        // Use the request body to build a new domain Book
        val replacement = convertReplaceRequestToBook(isbn, book)
        val replacedBook = service.replaceBook(isbn, replacement)
        return convertBookToResponse(replacedBook)
    }

    override fun partialUpdateBook(isbn: String, update: PartialBookUpdate): BookResponse {
        // Either map PartialBookUpdate to domain-level partial update, or let the service accept primitives
        val updatedBook = service.partialUpdateBook(
            isbn = isbn,
            title = update.title,
            author = update.author,
            price = update.price,
            image = update.image
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

    // Prefer changing BookstoreAPI to return ResponseEntity<ReviewResponse>
    override fun createReview(isbn: String, review: ReviewCreateRequest): ResponseEntity<ReviewResponse> {
        val createdReview = service.createReview(isbn, convertCreateRequestToReview(review))
        val response = convertReviewToResponse(createdReview)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdReview.id)
            .toUri()

        return ResponseEntity.created(location).body(response)
    }

    override fun replaceReview(isbn: String, id: Long, review: ReviewReplaceRequest): ReviewResponse {
        val replacement = convertReplaceRequestToReview(id, review)
        val replacedReview = service.replaceReview(isbn, id, replacement)
        return convertReviewToResponse(replacedReview)
    }

    override fun partialUpdateReview(isbn: String, id: Long, update: ReviewPartialUpdate): ReviewResponse {
        val updatedReview = service.partialUpdateReview(
            isbn = isbn,
            id = id,
            rating = update.rating,
            comment = update.comment
        )
        return convertReviewToResponse(updatedReview)
    }

    override fun deleteReview(isbn: String, id: Long) {
        service.deleteReview(isbn, id)
    }

    //**** Mapping functions ****

    private fun convertBookToResponse(book: Book): BookResponse =
        BookResponse(
            isbn = book.isbn,
            title = book.title,
            author = book.author,
            price = book.price,
            image = book.image,
            reviews = book.reviews.map { convertReviewToResponse(it) }
        )

    private fun convertReviewToResponse(review: Review): ReviewResponse =
        ReviewResponse(
            id = review.id,
            rating = review.rating,
            comment = review.comment
        )

    private fun convertCreateRequestToBook(request: BookCreateRequest): Book =
        Book(
            isbn = request.isbn,
            title = request.title,
            author = request.author,
            price = request.price,
            image = request.image
        )

    private fun convertReplaceRequestToBook(isbn: String, request: BookReplaceRequest): Book =
        Book(
            isbn = isbn,
            title = request.title,
            author = request.author,
            price = request.price,
            image = request.image
        )

    private fun convertCreateRequestToReview(request: ReviewCreateRequest): Review =
        Review(
            id = 0, // or null / default depending on your domain definition
            rating = request.rating,
            comment = request.comment
        )

    private fun convertReplaceRequestToReview(id: Long, request: ReviewReplaceRequest): Review =
        Review(
            id = id,
            rating = request.rating,
            comment = request.comment
        )
}
