package ru.sber.springsecurity.entity

class Client {
    private var login: String = ""
    private var password: String = ""

    fun getLogin(): String {
        return login
    }

    fun setLogin(login: String) {
        this.login = login
    }

    fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }
}
