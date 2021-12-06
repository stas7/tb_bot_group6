package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository

@Component
@Scope("singleton")

class InitState : StateInterface {
    @Autowired
    lateinit var customerRepository: CustomerRepository

    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val message = SendMessage(stateInfoDTO.chatId.toString(), "Выберите функционал:")
        message.replyMarkup = ReplyKeyboardMarkup
            .builder()
            .keyboardRow(
                KeyboardRow(
                    listOf(KeyboardButton("Мои Встречи"), KeyboardButton("Поиск/Организация Встречи"))
                )
            )
            .oneTimeKeyboard(true)
            .build()
        return message
    }

    override fun changeState(stateInfoDTO: StateInfoDTO) {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        customer.state = MachinesStateEnum.DECIDING_FROM_INIT
        val resultingCustomer = customerRepository.save(customer)
        println("------>$resultingCustomer")
    }
}
