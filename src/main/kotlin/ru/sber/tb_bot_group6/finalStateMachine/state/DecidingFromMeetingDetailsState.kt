package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
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
class DecidingFromMeetingDetailsState : StateInterface {
    @Autowired
    lateinit var customerRepository: CustomerRepository
    @Autowired
    lateinit var roleRepository: RoleRepository
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
        when (stateInfoDTO.receivedText) {
            "Главное Меню" -> {
                val message = SendMessage(stateInfoDTO.chatId.toString(), "переходим в главное меню")
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
            }
            else -> {
                val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
                val meeting = requireNotNull(customer.currentMeeting)
                val role = requireNotNull(roleRepository.findByCustomerIdAndMeetingId(customer.id, meeting.id))
                return when (role.role) {
                    RoleEnum.USER -> {
                        when (stateInfoDTO.receivedText) {
                            "Подписаться" -> {
                                role.role = RoleEnum.SUBSCRIBER
                                roleRepository.save(role)
                                val message = SendMessage(stateInfoDTO.chatId.toString(), "Вы подписались на встречу!\n" +
                                        "можете просмотреть ее в меню Мои Встречи")
                                message.replyMarkup = ReplyKeyboardMarkup
                                    .builder()
                                    .keyboardRow(
                                        KeyboardRow(
                                            listOf(KeyboardButton("Главное Меню"))
                                        )
                                    )
                                    .oneTimeKeyboard(true)
                                    .build()
                                message
                            }
                            else -> {
                                SendMessage(stateInfoDTO.chatId.toString(), "Неправильный ввод, попробуйте еще раз")
                            }
                        }
                    }
                    RoleEnum.SUBSCRIBER -> {
                        when (stateInfoDTO.receivedText) {
                            "Отписаться" -> {
                                role.role = RoleEnum.USER
                                roleRepository.save(role)
                                val message = SendMessage(stateInfoDTO.chatId.toString(), "Вы отписались от встречи!")
                                message.replyMarkup = ReplyKeyboardMarkup
                                    .builder()
                                    .keyboardRow(
                                        KeyboardRow(
                                            listOf(KeyboardButton("Главное Меню"))
                                        )
                                    )
                                    .oneTimeKeyboard(true)
                                    .build()
                                message
                            }
                            else -> {
                                SendMessage(stateInfoDTO.chatId.toString(), "Неправильный ввод, попробуйте еще раз")
                            }
                        }
                    }
                    RoleEnum.CREATOR -> {
                        when (stateInfoDTO.receivedText) {
                            // TODO: Deletion bug!
                            "Отменить встречу" -> {
//                                val roles = roleRepository.findByMeetingId(meeting.id)
//                                roleRepository.deleteAll(roles)
//                                meetingsRepository.delete(meeting)
//                                val message = SendMessage(stateInfoDTO.chatId.toString(), "Вы удалили встречу." +
//                                        "Информация о ней будет удалена у всех пользователей")
                                val message = SendMessage(stateInfoDTO.chatId.toString(), "Извините, фича еще не реализована :(")
                                message.replyMarkup = ReplyKeyboardMarkup
                                    .builder()
                                    .keyboardRow(
                                        KeyboardRow(
                                            listOf(KeyboardButton("Главное Меню"))
                                        )
                                    )
                                    .oneTimeKeyboard(true)
                                    .build()
                                message
                            }
                            "Редактировать встречу" -> {
                                SendMessage(stateInfoDTO.chatId.toString(), "Введите новое название встречи:")
                            }
                            else -> {
                                SendMessage(stateInfoDTO.chatId.toString(), "Неправильный ввод, попробуйте еще раз")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun changeState(stateInfoDTO: StateInfoDTO) {
        val customer = requireNotNull(customerRepository.findByTelegramChatId(stateInfoDTO.chatId))
        customer.state = when (stateInfoDTO.receivedText) {
            "Главное Меню" -> {
                MachinesStateEnum.INIT
            }
            else -> {
                val meeting = requireNotNull(customer.currentMeeting)
                val role = requireNotNull(roleRepository.findByCustomerIdAndMeetingId(customer.id, meeting.id))
                when (role.role) {
                    RoleEnum.USER -> {
                        when (stateInfoDTO.receivedText) {
                            "Подписаться" -> {
                                MachinesStateEnum.INIT
                            }
                            else -> {
                                MachinesStateEnum.DECIDING_FROM_MEETING_DETAILS
                            }
                        }
                    }
                    RoleEnum.SUBSCRIBER -> {
                        when (stateInfoDTO.receivedText) {
                            "Отписаться" -> {
                                MachinesStateEnum.INIT
                            }
                            else -> {
                                MachinesStateEnum.DECIDING_FROM_MEETING_DETAILS
                            }
                        }
                    }
                    RoleEnum.CREATOR -> {
                        when (stateInfoDTO.receivedText) {
                            "Отменить встречу" -> {
                                MachinesStateEnum.INIT
                            }
                            "Редактировать встречу" -> {
                                MachinesStateEnum.MEETING_CREATION_NAME
                            }
                            else -> {
                                MachinesStateEnum.DECIDING_FROM_MEETING_DETAILS
                            }
                        }
                    }
                }
            }
        }

        val resultingCustomer = customerRepository.save(customer)
        println("------>$resultingCustomer")
    }

}


