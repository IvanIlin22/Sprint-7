package ru.sber.springsecurity.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.sber.springsecurity.entity.AddressBook
import java.util.concurrent.ConcurrentHashMap
import kotlin.streams.toList

@Component
class AddressBookService {
    private val addressBook: ConcurrentHashMap<Int, AddressBook> = ConcurrentHashMap()
    private val logger = LoggerFactory.getLogger(javaClass)
    private var id: Int = 0

    public fun addAddressBook(address: AddressBook){
        address.setId(id)
        addressBook.put(id++, address)
        logger.info("Save address")
    }

    public fun listAddressBook(id: Int?, name: String?, phone: String?, address: String?): List<AddressBook> {
        var addresses: List<AddressBook> = ArrayList(addressBook.values)
        if (id != null)
            addresses = addresses.stream().filter { it.getId() == id }.toList()
        if (name != null)
            addresses = addresses.stream().filter { it.getName() == name }.toList()
        if (address != null)
            addresses = addresses.stream().filter { it.getAddress() == address }.toList()
        if (phone != null)
            addresses = addresses.stream().filter { it.getPhone() == phone }.toList()

        return addresses
    }

    public fun viewAddressBook(id: Int): AddressBook? {
        return addressBook[id]
    }

    public fun editAddressBook(id: Int, address: AddressBook) {
        addressBook[id] = address
        logger.info("Edit address with id {$id}")
    }

    public fun deleteAddressBook(id: Int) {
        addressBook.remove(id)
        logger.info("Delete address with id {$id}")
    }

}