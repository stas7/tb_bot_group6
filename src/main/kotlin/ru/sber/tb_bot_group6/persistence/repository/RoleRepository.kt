package ru.sber.tb_bot_group6.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.persistence.entity.MeetingEntity
import ru.sber.tb_bot_group6.persistence.entity.RoleEntity
import ru.sber.tb_bot_group6.persistence.key.CustomerMeetingKey

interface RoleRepository : JpaRepository<RoleEntity, CustomerMeetingKey> {
    fun findMeetingByCustomerIdAndRole(customerId: Long, role: RoleEnum): List<MeetingEntity>
}