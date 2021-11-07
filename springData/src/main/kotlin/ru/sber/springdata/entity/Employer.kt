package ru.sber.springdata.entity

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "employer")
class Employer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id")
    private var id: Int = 0,

    @Column(name = "company_name")
    private var companyName: String,

    @Column(name = "creation_time")
    private var creationTime: LocalDateTime,

    @OneToMany(mappedBy = "employer", cascade = [CascadeType.ALL], orphanRemoval = true)
    private var vacancies: MutableList<Vacancy> = ArrayList(),

    @Column(name = "block_time")
    private var blockTime: LocalDateTime? = null
) {
    fun getVacancies(): MutableList<Vacancy>? {
        return this.vacancies
    }

    override fun toString(): String {
        return "Employer(id = $id, companyName = $companyName, creationTime = $creationTime, blockTime = $blockTime)"
    }
}
