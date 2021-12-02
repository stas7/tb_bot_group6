package ru.sber.tb_bot_group6.persistence.key

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class CustomerMeetingKey(
    @Column(name="customer_id")
    val customerId: Long,
    @Column(name="meeting_id")
    val meetingId: Long
) : Serializable
