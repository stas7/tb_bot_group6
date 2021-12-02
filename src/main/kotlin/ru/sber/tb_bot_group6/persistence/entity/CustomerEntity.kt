package ru.sber.tb_bot_group6.persistence.entity

import org.hibernate.annotations.NaturalId
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import javax.persistence.*

@Entity
@Table(name = "customers")
final class CustomerEntity(
    @Id
//    @SequenceGenerator(name = "customer_id_gen", initialValue = 1000)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_id_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0,

    @ManyToMany
    @JoinTable(
        name = "customer_meetings",
        joinColumns = [JoinColumn(name = "customer_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "meeting_id", referencedColumnName = "id")]
    )
    val meetings: List<MeetingEntity> = listOf(),

//    @NaturalId
    // it's the state in final state machine
    @Enumerated(EnumType.ORDINAL)
    val state: MachinesStateEnum,

//    @NaturalId
    @Column(name = "telegram_name")
    val telegramName: String?,

//    @NaturalId
    @Column(name = "telegram_chat_id")
    val telegramChatId: Long?
)
