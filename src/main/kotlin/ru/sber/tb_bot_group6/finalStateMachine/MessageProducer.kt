package ru.sber.tb_bot_group6.finalStateMachine

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

interface MessageProducer {
    fun produce(update: Update): PartialBotApiMethod<Message>
}

class StubMessageProducer : MessageProducer {
    override fun produce(update: Update): PartialBotApiMethod<Message> {
        return SendMessage(update.message.chatId.toString(),"not implemented yet")
    }
}