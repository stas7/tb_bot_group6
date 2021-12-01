package ru.sber.tb_bot_group6.persistence.entity

import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
@Table(name = "customers")

final class CustomersEntity(
    @Id
    @SequenceGenerator(name = "customer_id_gen", initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_gen")
    var id: Long = 0,

    @NaturalId
    var state: Int,

    @NaturalId
    @Column(name = "telegram_name")
    var telegramName: String,

    @NaturalId
    @Column(name = "telegram_chat_id")
    var telegramChatId: Long = 0
)
