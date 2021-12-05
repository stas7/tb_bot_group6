package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.persistence.repository.MeetingRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@Component
class MeetingCreationDateTimeState : StateInterface{
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var meetingRepository: MeetingRepository
    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val currentMeeting = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId)
            ?.currentMeeting)
        currentMeeting.meetingDate = LocalDateTime.parse(stateInfoDTO.receivedText,
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
        meetingRepository.save(currentMeeting)

        val message = SendMessage(stateInfoDTO.chatId.toString(),
            "Встреча создана!")
        message.replyMarkup = ReplyKeyboardMarkup
            .builder()
            .keyboardRow(
                KeyboardRow(
                    listOf(KeyboardButton("/${currentMeeting.id}"))
                )
            )
            .oneTimeKeyboard(true)
            .build()

        return message
    }

    override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
        return MachinesStateEnum.MEETING_DETAILS
    }

}
