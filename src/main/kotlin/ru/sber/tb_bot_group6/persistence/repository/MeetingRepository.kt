package ru.sber.tb_bot_group6.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.entity.MeetingEntity

interface MeetingRepository : JpaRepository<MeetingEntity, Long> {
}