package ru.sber.tb_bot_group6.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.sber.tb_bot_group6.persistence.entity.CityEntity

@Repository
@Transactional

interface CityRepository : JpaRepository<CityEntity, Long> {
}
