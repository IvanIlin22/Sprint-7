package ru.sber.springsecurity.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.Cookie

@Component
class Authentication {
    private val clients: ConcurrentHashMap<String, String> = ConcurrentHashMap(mapOf("login1" to "password1", "login2" to "password2"))
    private val logger = LoggerFactory.getLogger(Authentication::class.java)
    private val cookie = Cookie("auth", LocalDateTime.now().toString())

    public fun authentication(login: String, password: String): Cookie? {
        if (clients[login] == password) {
            logger.info("the client $login is authenticated $password")
            return cookie
        }
        logger.info("the client $login is not authenticated $password")

        return null
    }
}
