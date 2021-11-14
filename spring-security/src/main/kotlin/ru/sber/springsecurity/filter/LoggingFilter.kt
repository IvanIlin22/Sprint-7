package ru.sber.springsecurity.filter

import org.slf4j.LoggerFactory
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter("/*")
class LoggingFilter : Filter {
    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request: HttpServletRequest = p0 as HttpServletRequest
        val response: HttpServletResponse = p1 as HttpServletResponse

        logger.info("Logging Request {} : {}", request.method, request.requestURI)
        p2!!.doFilter(request, response)
    }
}
