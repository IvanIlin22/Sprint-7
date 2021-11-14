package ru.sber.springsecurity

import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.security.test.context.support.WithMockUser


@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "user",roles = arrayOf("USER"))
class MvcMockApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun init() {
        mockMvc.perform(post("/app/add")
            .param("name", "Frank")
            .param("phone", "89263578922")
            .param("address", "Moscow"))

        mockMvc.perform(post("/app/add")
            .param("name", "Steven")
            .param("phone", "89263578931")
            .param("address", "London"))
    }

    @Test
    fun  testAddAddress() {
        mockMvc.perform(get("/app/add"))
            .andExpect(status().isOk)
            .andExpect(view().name("add"))

        mockMvc.perform(post("/app/add")
            .param("name", "Grigor")
            .param("phone", "892635789")
            .param("address", "Moscow"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(content().string(containsString("Moscow")))
            .andExpect(content().string(containsString("Grigor")))
    }

    @Test
    fun getListTest() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("list"))
            .andExpect(content().string(containsString("Frank")))
            .andExpect(content().string(containsString("Steven")))
    }

    @Test
    fun viewTest() {
        mockMvc.perform(get("/app/0/view"))
            .andExpect(status().isOk)
            .andExpect(view().name("result"))
            .andExpect(content().string(containsString("Frank")))
            .andExpect(content().string(containsString("89263578922")))
            .andExpect(content().string(containsString("Moscow")))
    }

    @Test
    @WithMockUser(username = "admin",roles = arrayOf("ADMIN"))
    fun deleteTest() {
        mockMvc.perform(get("/app/0/delete"))
            .andExpect(status().isOk)
            .andExpect(view().name("delete"))
            .andExpect(content().string(containsString("Адрес удален")))
    }

    @Test
    fun editTest() {
        mockMvc.perform(post("/app/0/edit").param("name", "Fedor"))
            .andExpect(status().isOk)
            .andExpect(view().name("result"))
            .andExpect(content().string(containsString("Fedor")))
    }

}
