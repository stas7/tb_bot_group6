package ru.template.telegram.bot.kotlin.template.event

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.sber.tb_bot_group6.finalStateMachine.StepCode

class TelegramReceivedCallbackEvent(
    val chatId: Long,
    val stepCode: StepCode,
    val callback: CallbackQuery
)
