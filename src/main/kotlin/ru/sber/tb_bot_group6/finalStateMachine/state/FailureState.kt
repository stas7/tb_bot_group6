package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO

@Component
class FailureState : StateInterface {

    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val message = SendMessage(stateInfoDTO.chatId.toString(), "Неправильный ввод, начнем сначала")
        message.replyMarkup = ReplyKeyboardMarkup
            .builder()
            .keyboardRow(
                KeyboardRow(
                    listOf(KeyboardButton("Главное Меню"))
                )
            )
            .oneTimeKeyboard(true)
            .build()
        return message
    }

    override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
        return MachinesStateEnum.INIT
    }
}
