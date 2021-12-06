package ru.sber.tb_bot_group6

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.persistence.entity.CityEntity
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.persistence.repository.MeetingRepository
import ru.sber.tb_bot_group6.persistence.repository.RoleRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@SpringBootTest
class TbBotGroup6ApplicationTests {
	@Autowired
	lateinit var roleRepository: RoleRepository
	@Autowired
	lateinit var meetingRepository: MeetingRepository
	@Autowired
	lateinit var customerRepository: CustomerRepository

	val customers = listOf(
		CustomerEntity(
			state = MachinesStateEnum.INIT,
			telegramName = "ss",
			telegramChatId = 123,
			currentCity = CityEntity(1, "Moscow")
		),
		CustomerEntity(
			state = MachinesStateEnum.INIT,
			telegramName = "asd",
			telegramChatId = 231,
			currentCity = CityEntity(2, "Saratov")
		))
	@Test
	fun contextLoads() {
//		repository.findAll().asSequence().map { listOf(it.role, it.customer.id, it.meeting.id) }.forEach { println(it) }
//		repository.findByCustomerAndRole(CustomerEntity(1, listOf(), MachinesStateEnum.INIT, "0", 0), RoleEnum.CREATOR).asSequence().map { it.meeting }.map { it.id }.forEach { println(it) }
		val roles = roleRepository.findByCustomerIdAndRole(1,  RoleEnum.SUBSCRIBER)
		println(roles)

//		meetingRepository.findByCityId(1).asSequence().forEach { println(it.id) }
//		meetingRepository.findByCityName("Moscow").asSequence().forEach { println(it.id) }

//		val list = customerRepository.saveAll(customers)
//		list.forEach { it.currentCity = CityEntity(3, "Perm") }
//		customerRepository.saveAll(list)

	}

	@Test
	fun lol() {
		println(LocalDateTime.parse("31.07.2016 14:15",
			DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
	}

}
