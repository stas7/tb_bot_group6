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
import ru.sber.tb_bot_group6.persistence.repository.MeetingRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle
import java.util.*

@Component
@Scope("singleton")

class MeetingCreationDateTimeState : StateInterface{
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var meetingRepository: MeetingRepository

    fun parseOrNull(textToDateTime: String): LocalDateTime? = try {
            LocalDateTime.parse(
                textToDateTime,
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            )
        } catch (e: DateTimeParseException) {
            null
        }


    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val currentMeeting = requireNotNull(customerRepository
            .findByTelegramChatId(stateInfoDTO.chatId)
            ?.currentMeeting)
        val message: SendMessage
        val dateTime = parseOrNull(stateInfoDTO.receivedText)

        if (dateTime != null) {
            currentMeeting.meetingDate = dateTime
            meetingRepository.save(currentMeeting)

            message = SendMessage(stateInfoDTO.chatId.toString(),
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
        } else {
            message = SendMessage(stateInfoDTO.chatId.toString(), "Ошибка форматирования, попробуйте снова!")
        }
        return message
    }

    override fun changeState(stateInfoDTO: StateInfoDTO) {
        if (parseOrNull(stateInfoDTO.receivedText) != null) {
            val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
            customer.state = MachinesStateEnum.MEETING_DETAILS
            val resultingCustomer = customerRepository.save(customer)
            println("------>$resultingCustomer")
        }
    }

}
