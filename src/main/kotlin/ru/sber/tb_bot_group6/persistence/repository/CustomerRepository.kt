package ru.sber.tb_bot_group6.persistence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.entity.RoleEntity
import ru.sber.tb_bot_group6.persistence.key.CustomerMeetingKey

interface CustomerRepository : JpaRepository<CustomerEntity, Long> {
}