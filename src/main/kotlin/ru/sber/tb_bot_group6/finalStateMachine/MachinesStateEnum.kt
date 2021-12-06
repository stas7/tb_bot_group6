package ru.sber.tb_bot_group6.finalStateMachine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.sber.tb_bot_group6.finalStateMachine.state.StateInterface
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository

// state in the final state machine
enum class MachinesStateEnum {
    INIT {
//        override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
//            val message = SendMessage(stateInfoDTO.chatId.toString(), "Выберите функционал:")
//            message.replyMarkup = ReplyKeyboardMarkup
//                .builder()
//                .keyboardRow(
//                    KeyboardRow(
//                        listOf(KeyboardButton("Мои Встречи"), KeyboardButton("Поиск/Организация Встречи"))
//                    )
//                )
//                .oneTimeKeyboard(true)
//                .build()
//            return message
//        }
//
//        override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
//            return DECIDING_FROM_INIT
//        }
    },
    FAILED {
//        override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
//            val message = SendMessage(stateInfoDTO.chatId.toString(), "Неправильный ввод, начнем сначала")
//            message.replyMarkup = ReplyKeyboardMarkup
//                .builder()
//                .keyboardRow(
//                    KeyboardRow(
//                        listOf(KeyboardButton("Главное Меню"))
//                    )
//                )
//                .oneTimeKeyboard(true)
//                .build()
//            return message
//        }
//
//        override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
//            return INIT
//        }
    },

    DECIDING_FROM_INIT {
//        override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
//            val message = SendMessage(stateInfoDTO.chatId.toString(),
//                "Вы выбрали: ${stateInfoDTO.receivedText}")
//            message.replyMarkup = ReplyKeyboardMarkup
//                .builder()
//                .keyboardRow(
//                    KeyboardRow(
//                        listOf(KeyboardButton("Далее"))
//                    )
//                )
//                .oneTimeKeyboard(true)
//                .build()
//            return message
//        }
//
//        override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
//            return when (stateInfoDTO.receivedText) {
//                "Мои Встречи" -> {
//                    MY_MEETINGS
//                }
//                "Поиск/Организация Встречи" -> {
//                    FAILED
//                }
//                else -> {
//                    FAILED
//                }
//            }
//        }
    },

    MY_MEETINGS {
//        @Autowired
//        lateinit var repository: CustomerRepository
//
//        override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
//            val customer = repository.findByTelegramChatId(stateInfoDTO.chatId)!!
//            val meetingsSting = customer.meetings.asSequence().map { "${ it.name } : \\${ it.id }\n" }
//            return SendMessage(stateInfoDTO.chatId.toString(), "Choose meeting:\n${ meetingsSting }")
//        }
//
//        override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
//            return FAILED
//        }
    },
    MEETING_DETAILS,
    LIST_OF_CITIES,
    CHOOSING_CITY,
    CREATE_OR_SEARCH_MEETING,
    MEETING_CREATION_NAME,
    MEETING_CREATION_ADDRESS,
    MEETING_CREATION_DATETIME,
    LIST_MEETINGS_IN_CITY,
    DECIDING_FROM_MEETING_DETAILS,


}
