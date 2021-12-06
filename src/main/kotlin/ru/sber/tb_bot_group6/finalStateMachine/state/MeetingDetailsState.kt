package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.RoleEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.persistence.repository.MeetingRepository
import ru.sber.tb_bot_group6.persistence.repository.RoleRepository

@Component
@Scope("singleton")

class MeetingDetailsState : StateInterface {
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var meetingsRepository: MeetingRepository

    fun checkCorrectness(s: String): Long? {
        val slicedS = s.slice(1 until s.length)
        val supposedId = slicedS.toLongOrNull() ?: return null
        println("meeting id must be $slicedS")
        return if (meetingsRepository.findById(supposedId).isPresent) {
            println("meeting is present")
            supposedId
        } else {
            println("meeting is not present!!!")
            null
        }
    }

    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val meetingId = checkCorrectness(stateInfoDTO.receivedText)
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
//        val customerId = customer.id

        if (meetingId == null) {
            val message = SendMessage(stateInfoDTO.chatId.toString(), "Встречи с данным идентификатором не существует")
            message.replyMarkup = ReplyKeyboardMarkup
                .builder()
                .keyboard(
                    listOf(
                        KeyboardRow(
                            listOf(KeyboardButton("Главное Меню"))
                        )
                    )
                )
                .oneTimeKeyboard(true)
                .build()
            return message
        } else {
            val role = requireNotNull(roleRepository.findByCustomerIdAndMeetingId(customer.id, meetingId))
            val meeting = role.meeting

            customer.currentMeeting = meeting
            customerRepository.save(customer)

            val meetingDetailsText = "name: ${meeting.name}\n" +
                    "city: ${meeting.city?.name}\n" +
                    "date: ${meeting.meetingDate}\n" +
                    "address: ${meeting.address}\n" +
                    "id: ${meeting.id}\n"
            val message = SendMessage(stateInfoDTO.chatId.toString(), meetingDetailsText)

            val keyboardRowForRole = when (role.role) {
                RoleEnum.USER -> {
                    KeyboardRow(
                        listOf(KeyboardButton("Подписаться"))
                    )
                }
                RoleEnum.SUBSCRIBER -> {
                    KeyboardRow(
                        listOf(KeyboardButton("Отписаться"))
                    )
                }
                RoleEnum.CREATOR -> {
                    KeyboardRow(
                        listOf(KeyboardButton("Отменить встречу"), KeyboardButton("Редактировать встречу"))
                    )
                }
            }
            message.replyMarkup = ReplyKeyboardMarkup
                .builder()
                .keyboard(
                    listOf(
                        keyboardRowForRole,
                        KeyboardRow(
                            listOf(KeyboardButton("Главное Меню"))
                        )
                    )
                )
                .oneTimeKeyboard(true)
                .build()
            return message

        }
    }

    override fun changeState(stateInfoDTO: StateInfoDTO) {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        val meetingId = checkCorrectness(stateInfoDTO.receivedText)
        customer.state = when(meetingId){
            null -> MachinesStateEnum.INIT
            else -> MachinesStateEnum.DECIDING_FROM_MEETING_DETAILS
        }
        val resultingCustomer = customerRepository.save(customer)
        println("------>$resultingCustomer")
    }

}
