package ru.sber.tb_bot_group6.telegram

import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class FrontBotComponent : TelegramLongPollingBot() {
    override fun getBotToken(): String {
        return "5030581530:AAEMEufEDu7mMCtcJdeFK5o6gLnZq7fnTgs"
    }

    override fun getBotUsername(): String {
        return "Organ1zeMeetingBot"
    }

    override fun onUpdateReceived(update: Update?) {
        // TODO("Not yet implemented")
        println("update received")
    }

}