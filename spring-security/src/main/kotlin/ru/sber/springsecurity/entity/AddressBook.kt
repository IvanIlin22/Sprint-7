package ru.sber.springsecurity.entity

class AddressBook {
    private var id: Int = 0
    private var name: String = ""
    private var phone: String = ""
    private var address: String = ""

    public fun getId(): Int {
        return id
    }

    public fun setId(id: Int) {
        this.id = id
    }

    public fun getAddress(): String {
        return address
    }

    public fun setAddress(address: String) {
        this.address = address
    }

    public fun getName(): String {
        return name
    }

    public fun setName(name: String) {
        this.name = name
    }

    public fun getPhone(): String {
        return phone
    }

    public fun setPhone(phone: String) {
        this.phone = phone
    }

}
