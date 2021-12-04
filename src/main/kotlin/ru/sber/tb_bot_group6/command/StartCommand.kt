package ru.sber.tb_bot_group6.command


import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.sber.tb_bot_group6.finalStateMachine.MachinesStateEnum
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository

//посмотрел статью на хабре и так описываются команды в телеге

@Component
class StartCommand(
    private val customerRepository: CustomerRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(MachinesStateEnum.START.command, MachinesStateEnum.START.desc) {

    companion object {
        private val START_CODE = StepCode.START
    }

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        // если пользователь уже есть в бд нашего бота, то он переходит к следующему шагу, если нет, то записывается в бд
        if (customerRepository.isUserExist(chatId)) {
            customerRepository.updateUserStep(chatId, START_CODE)
        } else customerRepository.createUser(chatId)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = START_CODE)
        )
    }

}