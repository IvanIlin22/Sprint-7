package ru.sber.springsecurity.resource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.springsecurity.entity.AddressBook
import ru.sber.springsecurity.service.AddressBookService

@RestController
@RequestMapping("/api")
class ApiResource @Autowired constructor(private val addressBookService : AddressBookService){

    @PostMapping("/add")
    public fun addAddress(@RequestBody address: AddressBook) {
        addressBookService.addAddressBook(address)
    }

    @GetMapping("/list")
    public fun listAddress(
        @RequestParam(required = false) id: Int?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) address: String?
    ): ResponseEntity<List<AddressBook>> {
        return ResponseEntity(addressBookService.listAddressBook(id, name, phone, address), HttpStatus.OK)
    }

    @GetMapping("/{id}/view")
    public fun viewAddress(@PathVariable("id") id: Int): ResponseEntity<AddressBook> {
        val address = addressBookService.viewAddressBook(id)

        return ResponseEntity(address, HttpStatus.OK)
    }

    @PutMapping("/{id}/edit")
    public fun editAddress(@PathVariable("id") id: Int, @RequestBody address: AddressBook): ResponseEntity<AddressBook> {
        addressBookService.editAddressBook(id, address)

        return ResponseEntity(address, HttpStatus.OK)
    }

    @DeleteMapping("/{id}/delete")
    public fun deleteAddress(@PathVariable("id") id: Int) {
        addressBookService.deleteAddressBook(id)
    }

}
