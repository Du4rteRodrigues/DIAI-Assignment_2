package pt.unl.fct.iadi.bookstore

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
	info = Info(
		title = "Bookstore API",
		version = "1.0.0",
		description = "Bookstore service for books and reviews"
	),
	tags = [
		Tag(name = "books", description = "Operations on books"),
		Tag(name = "reviews", description = "Operations on reviews")
	]
)
@SpringBootApplication
class BookstoreApplication

fun main(args: Array<String>) {
	runApplication<BookstoreApplication>(*args)
}
