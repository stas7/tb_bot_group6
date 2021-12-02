package ru.sber.tb_bot_group6.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.entity.MeetingEntity
import ru.sber.tb_bot_group6.persistence.entity.RoleEntity
import ru.sber.tb_bot_group6.persistence.key.CustomerMeetingKey

interface RoleRepository : JpaRepository<RoleEntity, CustomerMeetingKey> {
//    fun findByCustomerAndRole(customer: CustomerEntity, role: RoleEnum): List<RoleEntity>
    fun findByCustomerIdAndRole(customerId: Long, role: RoleEnum): List<RoleEntity>
    fun findByCustomerId(customerId: Long): List<RoleEntity>
}