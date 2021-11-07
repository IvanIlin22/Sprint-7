package ru.sber.springdata

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.sber.springdata.repository.AreaRepository
import ru.sber.springdata.repository.EmployerRepository
import ru.sber.springdata.repository.VacancyRepository
import ru.sber.springdata.entity.Area
import ru.sber.springdata.entity.Employer
import ru.sber.springdata.entity.Vacancy
import java.time.LocalDateTime

@SpringBootApplication
class SpringDataApplication(private val vacancyRepository: VacancyRepository,
                            private val employerRepository: EmployerRepository,
                            private val areaRepository: AreaRepository,) : CommandLineRunner {
    override fun run(vararg args: String?) {
        var area = Area(name = "Санкт-Петербург")
        areaRepository.save(area)
        val employer = Employer(companyName = "JetBrains",
            creationTime = LocalDateTime.now().minusDays(10))
        employerRepository.save(employer)
        var vacancy = Vacancy(
            employer = employer,
            area = area,
            title = "Java разработчик",
            description = "Писать код на жаве",
            compensationFrom = 100000,
            compensationTo = 500000,
            compensationGross = false,
            creationTime = LocalDateTime.now(),
            archivingTime = LocalDateTime.now().plusDays(3)
        )

        vacancyRepository.save(vacancy)
        vacancy.setTitle("Kotlin разработчик")
        vacancyRepository.save(vacancy)
        println(vacancyRepository.findById(1))
        vacancyRepository.deleteById(1)
    }
}

fun main(args: Array<String>) {
    runApplication<SpringDataApplication>(*args)
}
