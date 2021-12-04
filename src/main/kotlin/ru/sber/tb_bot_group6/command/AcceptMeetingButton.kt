package ru.sber.tb_bot_group6.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.finalStateMachine.StepCode
import ru.sber.tb_bot_group6.finalStateMachine.TelegramStepMessageEvent
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository

@Component
class AcceptMeetingButton(
    private val customerRepository: CustomerRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(MachinesStateEnum.ACCEPT_MEETING_BUTTON.command, MachinesStateEnum.ACCEPT_MEETING_BUTTON.desc) {

    companion object {
        private val ACCEPT_MEETING_BUTTON_REQUEST = StepCode.ACCEPT_MEETING_BUTTON_REQUEST
    }

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        customerRepository.updateUserStep(chatId, ACCEPT_MEETING_BUTTON_REQUEST)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = ACCEPT_MEETING_BUTTON_REQUEST)
        )
    }
}