package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.persistence.repository.MeetingRepository

@Component
class ListMeetingsInCityState : StateInterface {
    @Autowired
    lateinit var meetingRepository: MeetingRepository
    @Autowired
    lateinit var customerRepository: CustomerRepository

    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        val currentCity = requireNotNull(customer.currentCity)

        val meetings = meetingRepository.findByCityId(currentCity.id).asSequence()
        val meetingsSting = meetings
            .map { "${ it.name } : /${ it.id }" }
            .joinToString("\n")
        return SendMessage(stateInfoDTO.chatId.toString(), "Choose meeting:\n $meetingsSting")
    }

    override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
        return MachinesStateEnum.MEETING_DETAILS
    }

}
