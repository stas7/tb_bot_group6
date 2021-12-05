package ru.sber.tb_bot_group6.api

import javax.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sber.tb_bot_group6.properties.BotProperty
import ru.sber.tb_bot_group6.service.ReceiverService

@Component
class TelegramSender(
    private val botProperty: BotProperty,
    private val botCommands: List<IBotCommand>,
    private val receiverService: ReceiverService
) : TelegramLongPollingCommandBot() {

    @PostConstruct
    fun initCommands() {
        botCommands.forEach {
            register(it)
        }

        registerDefaultAction { absSender, message ->

            val commandUnknownMessage = SendMessage()
            commandUnknownMessage.chatId = message.chatId.toString()
            commandUnknownMessage.text = "Command '" + message.text.toString() + "' unknown"

            absSender.execute(commandUnknownMessage)
        }
    }

    override fun getBotToken() = botProperty.token

    override fun getBotUsername() = botProperty.username

    override fun processNonCommandUpdate(update: Update) {
        receiverService.execute(update)
    }
}