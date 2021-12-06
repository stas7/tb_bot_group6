package ru.sber.tb_bot_group6.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.entity.MeetingEntity
import ru.sber.tb_bot_group6.persistence.entity.RoleEntity
import ru.sber.tb_bot_group6.persistence.key.CustomerMeetingKey

@Repository
@Transactional

interface RoleRepository : JpaRepository<RoleEntity, CustomerMeetingKey> {
//    fun findByCustomerAndRole(customer: CustomerEntity, role: RoleEnum): List<RoleEntity>
//    @Query("SELECT r FROM customer_meetings ")
    fun findByCustomerIdAndRole(customerId: Long, role: RoleEnum): List<RoleEntity>
    fun findByCustomerId(customerId: Long): List<RoleEntity>
    fun findByCustomerIdAndMeetingId(customerId: Long, meetingId: Long): RoleEntity?
    fun findByMeetingId(meetingId: Long): List<RoleEntity>

}