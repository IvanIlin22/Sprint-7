package ru.sber.springsecurity.resource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.springsecurity.entity.AddressBook
import ru.sber.springsecurity.service.AddressBookService

@Controller
@RequestMapping(value = ["/app"])
class AppResource @Autowired constructor(private val addressBookService : AddressBookService){

    @RequestMapping(value = ["/add"], method = [RequestMethod.GET])
    public fun addAddressGet(model: Model): String {
        model.addAttribute("address", AddressBook())

        return "add"
    }

    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    public fun addAddress(@ModelAttribute address: AddressBook, model: Model): String {
        model.addAttribute("address", address)
        addressBookService.addAddressBook(address)

        return "result"
    }

    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    public fun listAddress(@RequestParam(required = false) id: Int?,
                           @RequestParam(required = false) name: String?,
                           @RequestParam(required = false) phone: String?,
                           @RequestParam(required = false) address: String?,
                           model: Model): String {
        val addresses = addressBookService.listAddressBook(id, name, phone, address)
        model.addAttribute("addresses", addresses)

        return "list"
    }

    @RequestMapping(value = ["/{id}/view"], method = [RequestMethod.GET])
    public fun viewAddress(@PathVariable("id") id: Int, model: Model): String {
        val address = addressBookService.viewAddressBook(id)
        model.addAttribute("address", address)

        return "result"
    }

    @RequestMapping(value = ["/{id}/edit"], method = [RequestMethod.GET])
    public fun editAddressGet(@PathVariable("id") id: Int, model: Model): String {
        model.addAttribute("id", id.toString())

        return "edit"
    }

    @RequestMapping(value = ["/{id}/edit"], method = [RequestMethod.POST])
    public fun editAddress(@PathVariable("id") id: Int, @ModelAttribute address: AddressBook, model: Model): String {
        addressBookService.editAddressBook(id, address)
        model.addAttribute("address", address)

        return "result"
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = ["/{id}/delete"], method = [RequestMethod.GET])
    public fun deleteAddress(@PathVariable("id") id: Int, model: Model): String {
        addressBookService.deleteAddressBook(id)
        model.addAttribute("delete", "Адрес удален")

        return "delete"
    }
}
