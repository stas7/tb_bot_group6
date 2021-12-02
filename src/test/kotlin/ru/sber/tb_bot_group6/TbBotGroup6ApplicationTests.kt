package ru.sber.tb_bot_group6

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.persistence.repository.MeetingRepository
import ru.sber.tb_bot_group6.persistence.repository.RoleRepository

@SpringBootTest
class TbBotGroup6ApplicationTests {
	@Autowired
	lateinit var roleRepository: RoleRepository
	@Autowired
	lateinit var meetingRepository: MeetingRepository


	@Test
	fun contextLoads() {
//		repository.findAll().asSequence().map { listOf(it.role, it.customer.id, it.meeting.id) }.forEach { println(it) }
//		repository.findByCustomerAndRole(CustomerEntity(1, listOf(), MachinesStateEnum.INIT, "0", 0), RoleEnum.CREATOR).asSequence().map { it.meeting }.map { it.id }.forEach { println(it) }
//		roleRepository.findByCustomerIdAndRole(1,  RoleEnum.CREATOR)
		meetingRepository.findByCityId(1).asSequence().forEach { println(it.id) }
		meetingRepository.findByCityName("Moscow").asSequence().forEach { println(it.id) }

	}

}
