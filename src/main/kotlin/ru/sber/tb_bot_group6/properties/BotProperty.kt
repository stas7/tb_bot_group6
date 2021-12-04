package ru.sber.tb_bot_group6.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "bot")
data class BotProperty(
    var username: String = "",
    var token: String = ""
)
