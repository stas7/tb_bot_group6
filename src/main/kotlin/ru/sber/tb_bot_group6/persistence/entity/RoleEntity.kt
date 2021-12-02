package ru.sber.tb_bot_group6.persistence.entity

import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.persistence.key.CustomerMeetingKey
import javax.persistence.*

@Entity
@Table(name = "customer_meetings")
data class RoleEntity(
    @EmbeddedId
    val id: CustomerMeetingKey,

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    val customer: CustomerEntity,

    @ManyToOne
    @MapsId("meetingId")
    @JoinColumn(name = "meeting_id")
    val meeting: MeetingEntity,

    val role: RoleEnum
)