package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CityRepository
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository

@Component
class ChoosingCityState : StateInterface {

    @Autowired
    lateinit var cityRepository: CityRepository

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var failureState: FailureState

    fun checkCorrectness(s: String): Long? {
        val slicedS = s.slice(1 until s.length)
        val supposedId = slicedS.toLongOrNull() ?: return null
        println("city id must be $slicedS")
        return if (cityRepository.findById(supposedId).isPresent) {
            println("city is present")
            supposedId
        } else {
            println("city is not present!!!")
            null
        }
    }

    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        val text = stateInfoDTO.receivedText
        val cityId = checkCorrectness(text)
        println("City id after check = $cityId")
        val customer = customerRepository.findByTelegramChatId(stateInfoDTO.chatId)

        if (cityId == null) {
            return failureState.getAnswer(stateInfoDTO)
        } else {
            customer!!.currentCity = cityRepository.findById(cityId).get()
            println("City from db = ${ customer.currentCity }")
            val customerFromRepo = customerRepository.save(customer)
            println("Customer ciry inside db = ${customerFromRepo.currentCity}")
        }

        val message = SendMessage(stateInfoDTO.chatId.toString(), "Выберите функционал:")
        message.replyMarkup = ReplyKeyboardMarkup
            .builder()
            .keyboardRow(
                KeyboardRow(
                    listOf(KeyboardButton("Создание Встречи"), KeyboardButton("Поиск Встречи"))
                )
            )
            .oneTimeKeyboard(true)
            .build()
        return message
    }

    override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
        val text = stateInfoDTO.receivedText
        val cityId = checkCorrectness(text)

        if (cityId == null) {
            return failureState.newState(stateInfoDTO)
        }

        return MachinesStateEnum.CREATE_OR_SEARCH_MEETING
    }

}
