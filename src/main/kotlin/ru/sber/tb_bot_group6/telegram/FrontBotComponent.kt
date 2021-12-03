package ru.sber.tb_bot_group6.telegram

import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendLocation
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sber.tb_bot_group6.finalStateMachine.MessageProducer
import ru.sber.tb_bot_group6.finalStateMachine.StubMessageProducer

@Component
class FrontBotComponent : TelegramLongPollingBot() {
    override fun getBotToken(): String {
        return "5030581530:AAEMEufEDu7mMCtcJdeFK5o6gLnZq7fnTgs"
    }

    override fun getBotUsername(): String {
        return "Organ1zeMeetingBot"
    }

    override fun onUpdateReceived(update: Update) {
        println("update received")

        val message: PartialBotApiMethod<Message>
        // TODO: implement MessageProducer
        val messageProducer: MessageProducer = StubMessageProducer()
        message = messageProducer.produce(update)

        when (message) {
            is SendMessage  ->  execute(message)
            is SendPhoto    ->  execute(message)
            is SendLocation ->  execute(message)
        }
    }
}