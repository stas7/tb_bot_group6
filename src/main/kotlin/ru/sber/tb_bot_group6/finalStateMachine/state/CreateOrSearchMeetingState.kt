package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.entity.MeetingEntity
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.persistence.repository.MeetingRepository
import ru.sber.tb_bot_group6.persistence.repository.RoleRepository

@Component
@Scope("singleton")

class CreateOrSearchMeetingState : StateInterface {
    @Autowired
    lateinit var failureState: FailureState

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var meetingRepository: MeetingRepository

    @Autowired
    lateinit var roleRepository: RoleRepository


    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        val currentCity = requireNotNull(customer.currentCity)
        return when (stateInfoDTO.receivedText) {
            "Создание Встречи" -> {

                val newMeeting = meetingRepository.save(
                    MeetingEntity(
                        name = null,
                        city = currentCity,
                        address = null,
                        meetingDate = null
                    )
                )
                customer.currentMeeting = newMeeting
                customer.meetings.add(newMeeting)
                customerRepository.save(customer)

                val role = requireNotNull(roleRepository.findByCustomerIdAndMeetingId(customer.id, newMeeting.id))
                role.role = RoleEnum.CREATOR
                roleRepository.save(role)

                SendMessage(stateInfoDTO.chatId.toString(),
                    "Введите желаемое название встречи:")
            }
            "Поиск Встречи" -> {
                val meetingsString = meetingRepository
                    .findByCityId(currentCity.id)
                    .asSequence()
                    .map { "${it.name} : /${it.id}" }
                    .joinToString("\n")

                SendMessage(stateInfoDTO.chatId.toString(),
                "Выберите встречу:\n$meetingsString")
            }
            else -> {
                failureState.getAnswer(stateInfoDTO)
            }
        }
    }

    override fun changeState(stateInfoDTO: StateInfoDTO) {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        customer.state =  when (stateInfoDTO.receivedText) {
            "Создание Встречи" -> {
                MachinesStateEnum.MEETING_CREATION_NAME
            }
            "Поиск Встречи" -> {
                MachinesStateEnum.LIST_MEETINGS_IN_CITY
            }
            else -> {
                MachinesStateEnum.INIT
            }
        }
        val resultingCustomer = customerRepository.save(customer)
        println("------>$resultingCustomer")
    }
}
