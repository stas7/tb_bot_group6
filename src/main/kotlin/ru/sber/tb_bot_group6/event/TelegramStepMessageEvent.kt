package ru.template.telegram.bot.kotlin.template.event

import ru.sber.tb_bot_group6.finalStateMachine.StepCode


class TelegramStepMessageEvent(
    val chatId: Long,
    val stepCode: StepCode
)
