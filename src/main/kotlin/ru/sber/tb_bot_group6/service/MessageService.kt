package ru.sber.tb_bot_group6.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.sber.tb_bot_group6.api.TelegramSender
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.dto.markup.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.enums.StepType.*
import ru.template.telegram.bot.kotlin.template.event.TelegramStepMessageEvent
import ru.template.telegram.bot.kotlin.template.strategy.MarkupContext
import ru.template.telegram.bot.kotlin.template.strategy.MessageContext
import ru.template.telegram.bot.kotlin.template.strategy.NextStepContext

@Service
class MessageService(
    private val telegramSender: TelegramSender, // отправщик сообщения
    private val messageContext: MessageContext, // Формирование текстовок сообщения
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val markupContext: MarkupContext<DataModel>, // Формирование текстовок с кнопками
    private val nextStepContext: NextStepContext // Выбор следующего этапа
) {

    fun sendMessageToBot(
        chatId: Long,
        stepCode: StepCode
    ) {
        when (stepCode.type) {
            // Простое сообщение
            SIMPLE_TEXT -> telegramSender.execute(simpleTextMessage(chatId))
            // Сообщение с кнопками
            INLINE_KEYBOARD_MARKUP -> telegramSender.sendInlineKeyboardMarkup(chatId)
        }

        if (!stepCode.botPause) { // если нет паузы, то формируем следующее сообщение
            applicationEventPublisher.publishEvent(
                TelegramStepMessageEvent(
                    chatId = chatId,
                    stepCode = nextStepContext.next(chatId, stepCode)!!
                )
            )
        }
    }

    // SendMessage - объект телеграм АПИ для отправки сообщения
    private fun simpleTextMessage(chatId: Long): SendMessage {
        val sendMessage = SendMessage()
        sendMessage.chatId = chatId.toString()
        sendMessage.text = messageContext.getMessage(chatId)
        sendMessage.enableHtml(true)

        return sendMessage
    }

    // Отправляем в бота сообщение с кнопками
    private fun TelegramSender.sendInlineKeyboardMarkup(chatId: Long) {
        val inlineKeyboardMarkup: InlineKeyboardMarkup
        val messageText: String

        val inlineKeyboardMarkupDto = markupContext.getInlineKeyboardMarkupDto(chatId)!!
        messageText = inlineKeyboardMarkupDto.message
        inlineKeyboardMarkup = inlineKeyboardMarkupDto.inlineButtons.getInlineKeyboardMarkup()

        this.execute(sendMessageWithMarkup(chatId, messageText, inlineKeyboardMarkup))
    }

    private fun sendMessageWithMarkup(
        chatId: Long, messageText: String, inlineKeyboardMarkup: InlineKeyboardMarkup
    ): BotApiMethod<Message> {
        val sendMessage = SendMessage()
        sendMessage.chatId = chatId.toString()
        sendMessage.text = messageText

        sendMessage.replyMarkup = inlineKeyboardMarkup
        sendMessage.parseMode = ParseMode.HTML
        return sendMessage
    }

    // Формируем модель кнопок
    private fun List<MarkupDataDto>.getInlineKeyboardMarkup(): InlineKeyboardMarkup {
        val inlineKeyboardMarkup = InlineKeyboardMarkup()
        val inlineKeyboardButtonsInner: MutableList<InlineKeyboardButton> = mutableListOf()
        val inlineKeyboardButtons: MutableList<MutableList<InlineKeyboardButton>> = mutableListOf()
        this.sortedBy { it.rowPos }.forEach { markupDataDto ->
            val button = InlineKeyboardButton()
                .also { it.text = markupDataDto.text }
                .also { it.callbackData = markupDataDto.text }
            inlineKeyboardButtonsInner.add(button)
        }
        inlineKeyboardButtons.add(inlineKeyboardButtonsInner)
        inlineKeyboardMarkup.keyboard = inlineKeyboardButtons
        return inlineKeyboardMarkup
    }
}