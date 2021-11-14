package ru.sber.springsecurity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ru.sber.springsecurity.entity.AddressBook

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class MvcApplicationTests {
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private var headers: HttpHeaders = HttpHeaders()

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    fun getCookieForUser(
        username: String,
        password: String,
        loginUrl: String
    ): String {
        val form: MultiValueMap<String, String> = LinkedMultiValueMap()
        form["username"] = username
        form["password"] = password
        val loginResponse: ResponseEntity<String> = restTemplate.postForEntity(
            loginUrl,
            HttpEntity(form, HttpHeaders()),
            String::class.java
        )
        return loginResponse.headers["Set-Cookie"]!![0]
    }

    @BeforeEach
    fun setUpCookie() {
        val cookie = getCookieForUser("admin", "admin", url("login"))
        headers.add("Cookie", cookie)

        val address = AddressBook()
        address.setId(0)
        address.setName("Pavel")
        address.setAddress("London")
        val resp = restTemplate.exchange(
            url("api/add"),
            HttpMethod.POST,
            HttpEntity(address, headers),
            AddressBook::class.java)
    }

    @Test
    fun testAddAddress() {
        val address = AddressBook()
        address.setId(0)
        address.setName("Fedor")
        address.setAddress("Moscow")
        val resp = restTemplate.exchange(
            url("api/add"),
            HttpMethod.POST,
            HttpEntity(address, headers),
            AddressBook::class.java)

        assertEquals(HttpStatus.OK, resp.statusCode)
    }

    @Test
    fun testGetList() {
        val resp = restTemplate.exchange(
            url("api/list"),
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            String::class.java)

        assertEquals(HttpStatus.OK, resp.statusCode)
        assertThat(resp.body).contains("Pavel")
    }

    @Test
    fun testView() {
        val resp = restTemplate.exchange(
            url("api/0/view"),
            HttpMethod.GET,
            HttpEntity<HttpHeaders>(headers),
            String::class.java)

        assertEquals(HttpStatus.OK, resp.statusCode)
        assertThat(resp.body).contains("London")
    }

    @Test
    fun testDelete() {
        val resp = restTemplate.exchange(
            url("api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, headers),
            String::class.java
        )
        assertEquals(HttpStatus.OK, resp.statusCode)
    }
}
