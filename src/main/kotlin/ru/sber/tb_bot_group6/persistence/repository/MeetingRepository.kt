package ru.sber.tb_bot_group6.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sber.tb_bot_group6.persistence.entity.CityEntity
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.entity.MeetingEntity

interface MeetingRepository : JpaRepository<MeetingEntity, Long> {
    fun findByCityId(cityId: Long): List<MeetingEntity>
    fun findByCityName(cityName: String): List<MeetingEntity>

}