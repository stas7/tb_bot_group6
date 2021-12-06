package ru.sber.tb_bot_group6.finalStateMachine

data class StateInfoDTO(
    val chatId: Long,
    val receivedText: String,
    val cityName: String? = null,
    val meetingId: Long? = null,
    val tgName: String? = null
)