package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.persistence.repository.MeetingRepository

@Component
@Scope("singleton")

class MeetingCreationNameState : StateInterface{
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var meetingRepository: MeetingRepository
    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val currentMeeting = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId)?.currentMeeting)
        currentMeeting.name = stateInfoDTO.receivedText
        meetingRepository.save(currentMeeting)
        return SendMessage(stateInfoDTO.chatId.toString(), "Введите адрес:")
    }

    override fun changeState(stateInfoDTO: StateInfoDTO) {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        customer.state = MachinesStateEnum.MEETING_CREATION_ADDRESS
        val resultingCustomer = customerRepository.save(customer)
        println("------>$resultingCustomer")
    }

}
