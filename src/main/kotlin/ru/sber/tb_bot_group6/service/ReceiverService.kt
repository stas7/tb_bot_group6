package ru.sber.tb_bot_group6.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.template.telegram.bot.kotlin.template.event.TelegramReceivedCallbackEvent
import ru.template.telegram.bot.kotlin.template.event.TelegramReceivedMessageEvent

@Service
class ReceiverService(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val usersRepository: CustomerRepository
) {

    fun execute(update: Update) {
        if (update.hasCallbackQuery()) {
            callbackExecute(update.callbackQuery)
        } else if (update.hasMessage()) {
            messageExecute(update.message)
        } else {
            throw IllegalStateException("Not yet supported")
        }
    }

    private fun messageExecute(message: Message) {
        val chatId = message.chatId
        val stepCode = usersRepository.getCustomer(chatId)!!.state
        applicationEventPublisher.publishEvent(
            TelegramReceivedMessageEvent(
                chatId = chatId,
                stepCode = stepCode,
                message = message
            )
        )
    }

    private fun callbackExecute(callback: CallbackQuery) {
        val chatId = callback.from.id
        val stepCode = usersRepository.getCustomer(chatId)!!.state
        applicationEventPublisher.publishEvent(
            TelegramReceivedCallbackEvent(chatId = chatId, stepCode = stepCode, callback = callback)
        )
    }
}