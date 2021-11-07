package dao

import org.hibernate.Session
import org.hibernate.SessionFactory
import java.io.Serializable

class GenericDao {
    private val sessionFactory: SessionFactory

    constructor(sessionFactory: SessionFactory) {
        this.sessionFactory = sessionFactory
    }

    fun <T> get(clazz: Class<T>, id: Serializable): T {
        val result: T
        getSession().use {
            it.beginTransaction()
            result = it.get(clazz, id)
            it.transaction.commit()
        }
        return result
    }

    fun save(obj: Any) {
        getSession().use {
            it.beginTransaction()
            it.save(obj)
            it.transaction.commit()
        }
    }

    fun update(obj: Any) {
        getSession().use {
            it.beginTransaction()
            it.update(obj)
            it.transaction.commit()
        }
    }

    fun delete(obj: Any) {
        getSession().use {
            it.beginTransaction()
            it.delete(obj)
            it.transaction.commit()
        }
    }

    fun getSession(): Session {
        return sessionFactory.currentSession
    }
}
