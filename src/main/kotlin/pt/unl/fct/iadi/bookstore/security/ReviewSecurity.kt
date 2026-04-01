package pt.unl.fct.iadi.bookstore.security

import org.springframework.stereotype.Component
import pt.unl.fct.iadi.bookstore.service.BookstoreService
import org.springframework.security.core.context.SecurityContextHolder

@Component
class ReviewSecurity(
    private val service: BookstoreService
) {

    fun isAuthor(reviewId: String, username: String): Boolean {
        val review = service.getReview(
            reviewId,
            id = TODO(),
        )
        return review.author == username
    }

    fun isAuthorOrAdmin(reviewId: String): Boolean {

        val review = service.getReview(
            reviewId,
            id = TODO()
        )

        val auth =
            SecurityContextHolder.getContext().authentication

        return review.author == auth.name ||
                auth.authorities.any { it.authority == "ROLE_ADMIN" }
    }
}