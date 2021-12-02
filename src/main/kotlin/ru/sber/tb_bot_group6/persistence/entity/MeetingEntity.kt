package ru.sber.tb_bot_group6.persistence.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "meetings")
final class MeetingEntity (
    @Id
//    @SequenceGenerator(name = "meeting_id_gen", initialValue = 1000)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_id_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,
    val name: String,

//    @Column(name = "city_id")
    @ManyToOne
//    @JoinColumn(name = "id")
    val city: CityEntity,

    val address: String,
    @Column(name = "meeting_date", columnDefinition = "TIMESTAMP")
    val meetingDate: LocalDateTime,

    @ManyToMany(mappedBy = "meetings")
    val customers: List<CustomerEntity> = listOf()

)