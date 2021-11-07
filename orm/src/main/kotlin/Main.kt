import dao.GenericDao
import entity.Area
import entity.Employer
import entity.Vacancy
import java.time.LocalDateTime

fun main(args: Array<String>) {
    val db = DbFactory()
    val session = db.createSessionFactory()

    session.use { session ->
        val dao = GenericDao(session!!)
        val employer = Employer(companyName = "Яндекс лавка",
            creationTime = LocalDateTime.now().minusDays(10))
        dao.save(employer)
        var area = Area(name = "Сочи")
        dao.save(area)
        var vacancy = Vacancy(
            employer = employer,
            area = area,
            title = "Водитель",
            description = "Водитель на камаз",
            compensationFrom = 10000,
            compensationTo = 50000,
            compensationGross = false,
            creationTime = LocalDateTime.now(),
            archivingTime = LocalDateTime.now().plusDays(3)
        )

        dao.save(vacancy)
        vacancy.setTitle("Экспедитор")
        dao.update(vacancy)
        dao.delete(vacancy)
    }
}
