package com.example.demo

import com.example.demo.Entity.Entity
import com.example.demo.EntityRepository.EntityRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class EntityRepositoryTest {
	@Autowired
	private lateinit var entityRepository: EntityRepository

	@Test
	fun `findById should find entity`() {
		val saveEntity = entityRepository.save(Entity(name = "name"))

		val foundEntity = entityRepository.findById(saveEntity.id!!)

		assertTrue { foundEntity.get() == saveEntity }
	}
}
