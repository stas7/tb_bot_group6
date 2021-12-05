package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.persistence.repository.MeetingRepository


@Component
class MeetingCreationAddressState : StateInterface{
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var meetingRepository: MeetingRepository
    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val currentMeeting = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId)?.currentMeeting)
        currentMeeting.address = stateInfoDTO.receivedText
        meetingRepository.save(currentMeeting)
        return SendMessage(stateInfoDTO.chatId.toString(), "Введите время и дату в формате m/d/yy h:M AM")
    }

    override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
        return MachinesStateEnum.MEETING_CREATION_DATETIME
    }

}
