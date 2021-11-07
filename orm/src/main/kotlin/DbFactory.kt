import entity.Area
import entity.Employer
import entity.Vacancy
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import java.util.List

class DbFactory {

    private val ENTITY_CLASSES_REGISTRY = List.of(
        Employer::class.java,
        Vacancy::class.java,
        Area::class.java
    )

    fun createSessionFactory(): SessionFactory? {
        val serviceRegistry = StandardServiceRegistryBuilder()
            .loadProperties("hibernate.properties")
            .build()
        val metadataSources = MetadataSources(serviceRegistry)
        ENTITY_CLASSES_REGISTRY.forEach(metadataSources::addAnnotatedClass)
        return metadataSources.buildMetadata().buildSessionFactory()
    }

}
