package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO

@Component
class DecidingFromInitState : StateInterface{

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

    override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
        return when (stateInfoDTO.receivedText) {
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
    }
}
