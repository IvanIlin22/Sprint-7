package ru.sber.springdata.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "area")
class Area(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private var id: Int = 0,

    private var name: String,

    @OneToOne(mappedBy = "area")
    private var vacancy: Vacancy? = null
) {
    override fun toString(): String {
        return "Area(id = $id, name = $name)"
    }
}
