package ru.sber.tb_bot_group6.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.entity.RoleEntity
import ru.sber.tb_bot_group6.persistence.key.CustomerMeetingKey
import java.util.*

@Repository
@Transactional

interface CustomerRepository : JpaRepository<CustomerEntity, Long> {
    fun findByTelegramChatId(telegramChatId: Long): CustomerEntity?
}