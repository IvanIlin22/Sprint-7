package ru.sber.springsecurity.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.sber.springsecurity.entity.User

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findUserByUsername(username: String): User?
}
