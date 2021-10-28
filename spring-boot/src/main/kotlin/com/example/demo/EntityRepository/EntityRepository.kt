package com.example.demo.EntityRepository

import com.example.demo.Entity.Entity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EntityRepository : JpaRepository<Entity, Long>
