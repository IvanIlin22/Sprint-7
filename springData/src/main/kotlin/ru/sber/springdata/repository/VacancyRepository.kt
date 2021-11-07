package ru.sber.springdata.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.sber.springdata.entity.Area
import ru.sber.springdata.entity.Vacancy

@Repository
interface VacancyRepository : CrudRepository<Vacancy, Int> {

    fun findVacancyByArea(area: Area): List<Vacancy>

    @Query("SELECT v FROM Vacancy v where v.compensationFrom= :compensationFrom")
    fun findVacancyByCompensationFrom(@Param("compensationFrom") compensationFrom: Int): List<Vacancy>
}
