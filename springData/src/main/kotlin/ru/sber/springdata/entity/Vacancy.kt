package ru.sber.springdata.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "vacancy")
class Vacancy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancy_id")
    private var id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private var employer: Employer,

    @OneToOne
    @JoinColumn(name = "area_id")
    private var area: Area,

    private var title: String,

    private var description: String,

    @Column(name = "compensation_from")
    private var compensationFrom: Int,

    @Column(name = "compensation_to")
    private var compensationTo: Int,

    @Column(name = "compensation_gross")
    private var compensationGross: Boolean,

    @Column(name = "creation_time")
    private var creationTime: LocalDateTime,

    @Column(name = "archiving_time")
    private var archivingTime: LocalDateTime
) {
    override fun toString(): String {
        return "Vacancy(id = $id, employer = $employer, area = $area, title = $title, description = $description," +
                "compensationFrom = $compensationFrom, compensationTo = $compensationTo, creationTime = $creationTime)"
    }

    fun setTitle(title: String) {
        this.title = title
    }
}
