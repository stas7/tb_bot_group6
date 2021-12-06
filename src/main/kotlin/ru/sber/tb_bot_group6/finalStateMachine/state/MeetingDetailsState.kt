package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.persistence.repository.RoleRepository

@Component
@Scope("singleton")

class MeetingDetailsState : StateInterface {
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var customerRepository: CustomerRepository

    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        return SendMessage(stateInfoDTO.chatId.toString(), "not implemented")
    }

    override fun changeState(stateInfoDTO: StateInfoDTO) {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        customer.state = MachinesStateEnum.FAILED
        val resultingCustomer = customerRepository.save(customer)
        println("------>$resultingCustomer")
    }

}
