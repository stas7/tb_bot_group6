package ru.sber.tb_bot_group6.finalStateMachine

class TelegramStepMessageEvent(
    val chatId: Long,
    val stepCode: StepCode
)