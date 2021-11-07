package ru.sber.springsecurity.servlets

import org.springframework.beans.factory.annotation.Autowired
import ru.sber.springsecurity.service.Authentication
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
    name = "AuthenticationServlet",
    urlPatterns = ["/login"]
)
class   AuthenticationServlet() : HttpServlet() {
    private lateinit var authentication: Authentication

    @Autowired
    constructor(auth: Authentication) : this()  {
        authentication = auth
    }

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        servletContext.getRequestDispatcher("/index.html").forward(req, resp)
    }


    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val login = req?.getParameter("login")
        val password = req?.getParameter("password")
        if (login == null || password == null) {
            resp!!.writer.println("There is no login or password")
            return
        }

        val cookie = authentication.authentication(login, password)
        if (cookie != null) {
            resp!!.addCookie(cookie)
            return
        }

        resp!!.writer.println("Error login or password")
        resp!!.writer.close()
    }

}
