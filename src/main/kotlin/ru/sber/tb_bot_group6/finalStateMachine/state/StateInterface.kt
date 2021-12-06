package ru.sber.tb_bot_group6.finalStateMachine.state

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StateInfoDTO

interface StateInterface {
    fun getAnswer(stateInfoDTO: StateInfoDTO): SendMessage
    fun changeState(stateInfoDTO: StateInfoDTO)
}