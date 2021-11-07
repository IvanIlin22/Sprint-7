package ru.sber.springdata.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.sber.springdata.entity.Area

@Repository
interface AreaRepository : CrudRepository<Area, Int>
