package ru.sber.tb_bot_group6.finalStateMachine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sber.tb_bot_group6.finalStateMachine.state.StateInterface
import ru.sber.tb_bot_group6.persistence.entity.CustomerEntity
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository

interface MessageProducer {
    fun produce(update: Update): PartialBotApiMethod<Message>
}
// TODO: Create MessageProducer Component
@Component
class StubMessageProducer : MessageProducer {
    override fun produce(update: Update): PartialBotApiMethod<Message> {
        return SendMessage(update.message.chatId.toString(),"not implemented yet")
    }
}

@Primary
@Component
@Scope("singleton")
class V1MessageProducer(
//    val customerRepository: CustomerRepository,
    val stateComponents: Map<MachinesStateEnum, StateInterface>,
    val stateProducer: StateProducer
) : MessageProducer {

    override fun produce(update: Update): PartialBotApiMethod<Message> {
        val chatId = update.message.chatId
        val tgName = update.message.from.userName
        val receivedText = update.message.text

        var state = stateProducer.getState(StateInfoDTO(chatId, receivedText, tgName = tgName))

        val message = stateComponents[state]!!.getAnswer(StateInfoDTO(chatId, receivedText))
        stateComponents[state]!!.changeState(StateInfoDTO(chatId, receivedText))

        return message
    }
}