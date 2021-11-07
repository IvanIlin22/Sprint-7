package ru.sber.springsecurity.filter

import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/app/*", "/api/*"])
class AuthenticationFilter : Filter {
    private val logger = LoggerFactory.getLogger(AuthenticationFilter::class.java)

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as HttpServletRequest
        val response = p1 as HttpServletResponse
        val cookies = request.cookies
        for (cookie in cookies) {
            if (cookie.name == "auth" && LocalDateTime.parse(cookie.value) < LocalDateTime.now()) {
                logger.info("auth cookie is true ${LocalDateTime.parse(cookie.value)} ${LocalDateTime.now()}")
                p2!!.doFilter(request, response)
                return
            }
        }

        response.sendRedirect("/login")
        p2!!.doFilter(request, response)
    }
}
