package ru.sber.tb_bot_group6

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.persistence.entity.RoleEntity
import ru.sber.tb_bot_group6.persistence.repository.RoleRepository

@SpringBootTest
class TbBotGroup6ApplicationTests {
	@Autowired
	lateinit var repository: RoleRepository

	@Test
	fun contextLoads() {
//		repository.findAll().asSequence().map { listOf(it.role, it.customer.id, it.meeting.id) }.forEach { println(it) }
		repository.findMeetingByCustomerIdAndRole(1, RoleEnum.CREATOR).asSequence().forEach { println(it) }

	}

}
