package ru.sber.tb_bot_group6.service

import org.springframework.stereotype.Service
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.entity.MeetingEntity
import java.time.LocalDateTime

@Service
// Doesn't it violate single responsibility principle?
interface StorageService {
    fun addCustomer(customer: CustomerEntity)
    fun setCustomerState(customerEntity: CustomerEntity, newState: MachinesStateEnum)

    fun createMeetingByCustomer(customer: CustomerEntity, meeting: MeetingEntity)
    fun editMeeting(
        meeting: MeetingEntity,
        newName: String? = null,
        newAddress: String? = null,
        newMeetingDate: LocalDateTime? = null
    )
    fun deleteMeeting(meeting: MeetingEntity)
}