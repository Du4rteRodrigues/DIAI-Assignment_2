package pt.unl.fct.iadi.bookstore.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ApiTokenFilter : OncePerRequestFilter() {

    private val tokens = mapOf(
        "token-catalog-abc123" to "catalog-app",
        "token-mobile-def456" to "mobile-app",
        "token-web-ghi789" to "web-app"
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token = request.getHeader("X-Api-Token")

        if (token == null || !tokens.containsKey(token)) {
            response.status = 401
            response.contentType = "application/json"
            response.writer.write(
                """{"error":"UNAUTHORIZED","message":"Missing or invalid X-Api-Token"}"""
            )
            return
        }

        request.setAttribute("appName", tokens[token])

        filterChain.doFilter(request, response)
    }
}