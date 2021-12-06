package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.persistence.repository.RoleRepository

@Component
@Scope("singleton")
class MyMeetingsState : StateInterface {
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var roleRepository: RoleRepository

    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        val meetingsCreated = roleRepository.findByCustomerIdAndRole(customer.id, RoleEnum.CREATOR).asSequence()
        val meetingsSubscribed = roleRepository.findByCustomerIdAndRole(customer.id, RoleEnum.SUBSCRIBER).asSequence()

        val meetingsSting = (meetingsCreated + meetingsSubscribed)
            .map { "${ it.meeting.name } : /${ it.meeting.id }" }
            .joinToString("\n")
        return SendMessage(stateInfoDTO.chatId.toString(), "Choose meeting:\n $meetingsSting")
    }

    override fun changeState(stateInfoDTO: StateInfoDTO) {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        customer.state = MachinesStateEnum.MEETING_DETAILS
        val resultingCustomer = customerRepository.save(customer)
        println("------>${resultingCustomer.id} ${resultingCustomer.state} ${resultingCustomer.currentCity?.id} ${resultingCustomer.currentMeeting?.id}")
    }
}
