package ru.sber.springsecurity.entity

import javax.persistence.CollectionTable
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.Table

@Entity
@Table(name = "t_user")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0

    var username: String = ""

    var password: String = ""

    var isActive: Boolean = false

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = arrayOf(JoinColumn(name = "user_id")))
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role> = HashSet()

    override fun toString(): String {
        return "User(id = $id, username = $username, password = $password)"
    }
}
