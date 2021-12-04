package ru.template.telegram.bot.kotlin.template.event

import org.telegram.telegrambots.meta.api.objects.Message
import ru.sber.tb_bot_group6.finalStateMachine.StepCode

class TelegramReceivedMessageEvent(
    val chatId: Long,
    val stepCode: StepCode,
    val message: Message
)
