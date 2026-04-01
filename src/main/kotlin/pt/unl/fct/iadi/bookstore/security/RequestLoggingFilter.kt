package pt.unl.fct.iadi.bookstore.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RequestLoggingFilter : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(RequestLoggingFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        filterChain.doFilter(request, response)

        val appName = request.getAttribute("appName") ?: "unknown"

        val principal =
            SecurityContextHolder.getContext().authentication?.name ?: "anonymous"

        logger.info(
            "[{}] [{}] {} {} [{}]",
            appName,
            principal,
            request.method,
            request.requestURI,
            response.status
        )
    }
}