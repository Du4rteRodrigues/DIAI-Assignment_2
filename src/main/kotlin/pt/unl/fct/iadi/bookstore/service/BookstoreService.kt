package pt.unl.fct.iadi.bookstore.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.bookstore.controller.dto.PartialBookUpdate
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewPartialUpdate
import pt.unl.fct.iadi.bookstore.domain.Book
import pt.unl.fct.iadi.bookstore.domain.Review
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class BookstoreService
{
    private val books = ConcurrentHashMap<String, Book>()
    private val reviews = ConcurrentHashMap<String, MutableMap<Long, Review>>()
    private val nextReviewId = AtomicLong(1)

    // US1
    fun listBooks(): List<Book> = books.values.toList()

    // US2
    fun createBook(request: Book): Book{
        if(books.containsKey(request.isbn)) throw BookAlreadyExistsException("Book already exists")
        val book = Book(request.isbn, request.title, request.author, request.price, request.image)
        books[book.isbn] = book
        return book
    }

    // US3
    fun getBook(isbn : String) : Book{
        return books[isbn] ?: throw BookNotFoundException("Book $isbn not found")
    }

    // US4
    fun replaceBook(isbn: String, book : Book): Book{
        if(book.isbn != isbn)throw ValidationException("${book.isbn} is not equal to isbn")
        books[isbn] = book
        return book
    }

    // US5
    fun partialUpdateBook(isbn: String, partial: PartialBookUpdate) : Book{
        val existing = getBook(isbn)
        val updated = existing.copy(
            title = partial.title?: existing.title,
            author = partial.author?: existing.author,
            price = partial.price?: existing.price,
            image = partial.image ?: existing.image)
        books[isbn] = updated
        return updated
    }

    // US6
    fun deleteBook(isbn: String){
        getBook(isbn)
        books.remove(isbn)
        reviews.remove(isbn)
    }

    // US7
    fun listReviews(isbn: String): List<Review>{
        getBook(isbn)
        return reviews.getOrDefault(isbn, emptyMap()).values.toList()
    }

    fun getReview(isbn: String, id: Long): Review {
        getBook(isbn)

        val bookReviews = reviews[isbn] ?: throw ReviewNotFoundException("Review for $isbn not found")

        return bookReviews[id] ?: throw ReviewNotFoundException("Review $id for $isbn not found")
    }


    // US8
    fun createReview(isbn: String, review: Review): Review{
        getBook(isbn)
        val id = nextReviewId.getAndIncrement()
        val newReview = Review(id, review.rating, review.comment)
        reviews.computeIfAbsent(isbn){ ConcurrentHashMap() }[id] = newReview
        return newReview
    }

    // US9
    fun replaceReview(isbn: String, id: Long, review: Review): Review{
        getBook(isbn)
        val reviews = reviews[isbn] ?: throw ReviewNotFoundException("Review for $isbn not found")
        getReview(isbn, id)
        val newReview = Review(id, review.rating, review.comment)
        reviews[id] = newReview
        return newReview
    }

    // US10
    fun partialUpdateReview(isbn: String, id: Long, partial: ReviewPartialUpdate): Review{
        getBook(isbn)
        val reviews = reviews[isbn] ?: throw ReviewNotFoundException("Review for $isbn not found")
        val existingReview = reviews[id] ?: throw ReviewNotFoundException("Review $id for $isbn not found")
        val updatedReview = existingReview.copy(
            rating = partial.rating ?: existingReview.rating,
            comment = partial.comment ?: existingReview.comment
        )
        reviews[id] = updatedReview
        return updatedReview
    }

    // US11
    fun deleteReview(isbn: String, id: Long){
        getBook(isbn)
        val reviews = reviews[isbn] ?: throw ReviewNotFoundException("Review for $isbn not found")
        if(!reviews.containsKey(id)){throw ReviewNotFoundException("Review $id for $isbn not found")}
        reviews.remove(id)
    }
}