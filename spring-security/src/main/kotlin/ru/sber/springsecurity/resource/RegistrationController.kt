package ru.sber.springsecurity.resource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import ru.sber.springsecurity.entity.Role
import ru.sber.springsecurity.entity.User
import ru.sber.springsecurity.repository.UserRepository
import java.util.*

@Controller
class RegistrationController {
    @Autowired
    private val userRepo: UserRepository? = null

    @RequestMapping(value = ["/registration"], method = [RequestMethod.GET])
    fun registration(model: Model): String {
        model.addAttribute("user", User())
        return "registration"
    }

    @RequestMapping(value = ["/registration"], method = [RequestMethod.POST])
    fun addUser(@ModelAttribute user: User, model: Model): String {
        val userFromDb = userRepo!!.findUserByUsername(user.username)
        if (userFromDb != null) {
            model.addAttribute("message", "User is exists")
            return "registration"
        }
        user.isActive = true
        user.roles = Collections.singleton(Role.ROLE_USER)
        user.password = BCryptPasswordEncoder().encode(user.password)
        userRepo.save(user)

        return "redirect:/login"
    }
}
