package ru.sber.tb_bot_group6.finalStateMachine.state

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO
import ru.sber.tb_bot_group6.persistence.repository.CityRepository
import ru.sber.tb_bot_group6.persistence.repository.RoleRepository

@Component
class ListOfCitiesState : StateInterface {
    @Autowired
    lateinit var cityRepository: CityRepository
    override fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage {
        return SendMessage(stateInfoDTO.chatId.toString(),
            cityRepository.findAll().map { "${it.name} : /${it.id}" }.joinToString("\n"))
    }

    override fun newState(stateInfoDTO: StateInfoDTO): MachinesStateEnum {
        return MachinesStateEnum.CHOOSING_CITY
    }

}
