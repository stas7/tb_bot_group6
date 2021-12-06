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

class DecidingFromInitState : StateInterface{
    @Autowired
    lateinit var customerRepository: CustomerRepository

    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val message = SendMessage(stateInfoDTO.chatId.toString(),
            "Вы выбрали: ${stateInfoDTO.receivedText}")
        message.replyMarkup = ReplyKeyboardMarkup
            .builder()
            .keyboardRow(
                KeyboardRow(
                    listOf(KeyboardButton("Далее"))
                )
            )
            .oneTimeKeyboard(true)
            .build()
        return message
    }

    override fun changeState(stateInfoDTO: StateInfoDTO) {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        customer.state =  when (stateInfoDTO.receivedText) {
            "Мои Встречи" -> {
                MachinesStateEnum.MY_MEETINGS
            }
            "Поиск/Организация Встречи" -> {
                MachinesStateEnum.LIST_OF_CITIES
            }
            else -> {
                MachinesStateEnum.FAILED
            }
        }
        val resultingCustomer = customerRepository.save(customer)
        println("------>$resultingCustomer")
    }
}
