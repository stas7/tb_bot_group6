package ru.sber.tb_bot_group6.persistence.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "meetings")
final class MeetingEntity (
    @Id
    val id: Long,
    val name: String,

    @Column(name = "city_id")
    @ManyToOne
    @JoinColumn(name = "id")
    val city: CityEntity,
    val address: String,
    @Column(name = "meeting_date", columnDefinition = "TIMESTAMP")
    val meetingDate: LocalDateTime,

    @ManyToMany(mappedBy = "meetings")
    val customers: List<CustomerEntity> = listOf()

)