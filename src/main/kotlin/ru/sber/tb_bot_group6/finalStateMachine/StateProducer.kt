package ru.sber.tb_bot_group6.finalStateMachine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository

@Component
class StateProducer {
    @Autowired
    lateinit var customerRepository: CustomerRepository

    fun getState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
        var customerFromRepository: CustomerEntity? = customerRepository.findByTelegramChatId(stateInfoDTO.chatId)
        val state: MachinesStateEnum

        if (customerFromRepository == null) {
            state = MachinesStateEnum.FAILED
            customerFromRepository = customerRepository.save(CustomerEntity(state = state, telegramChatId = stateInfoDTO.chatId, telegramName = requireNotNull(stateInfoDTO.tgName)))
        } else {
            state = customerFromRepository.state
        }
        return state
    }
}