package ru.sber.tb_bot_group6.listener

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.sber.tb_bot_group6.persistence.repository.CustomerRepository
import ru.sber.tb_bot_group6.service.ReceiverService
import ru.template.telegram.bot.kotlin.template.enums.ExecuteStatus
import ru.template.telegram.bot.kotlin.template.event.TelegramReceivedCallbackEvent
import ru.template.telegram.bot.kotlin.template.event.TelegramReceivedMessageEvent
import ru.template.telegram.bot.kotlin.template.event.TelegramStepMessageEvent
import ru.template.telegram.bot.kotlin.template.strategy.LogicContext
import ru.template.telegram.bot.kotlin.template.strategy.NextStepContext

@Component
class ApplicationListener(
    private val logicContext: LogicContext, // Основная бизнес логика
    private val nextStepContext: NextStepContext, // Выбор следующего этапа
    private val customerRepository: CustomerRepository, // Слой СУБД
    private val messageService: ReceiverService // Сервис, который формирует объект для отрпавки сообщения в бота
) {

    // Слушаем событие TelegramReceivedMessageEvent
    inner class Message {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedMessageEvent) {
            logicContext.execute(chatId = event.chatId, message = event.message)
            val nextStepCode = nextStepContext.next(event.chatId, event.stepCode)
            if (nextStepCode != null) {
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = nextStepCode
                    )
                )
            }
        }
    }

    // Слушаем событие TelegramStepMessageEvent
    inner class StepMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramStepMessageEvent) {
            // Обновляем шаг
            customerRepository.updateUserStep(event.chatId, event.stepCode)
            // Отправляем сообщение в бота (и формируем)
            messageService.sendMessageToBot(event.chatId, event.stepCode)
        }
    }

    // Слшуаем событие TelegramReceivedCallbackEvent
    inner class CallbackMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedCallbackEvent) {
            val nextStepCode = when (logicContext.execute(event.chatId, event.callback)) {
                ExecuteStatus.FINAL -> { // Если бизнес процесс одобрил переход на новый этап
                    nextStepContext.next(event.chatId, event.stepCode)
                }
                ExecuteStatus.NOTHING -> throw IllegalStateException("Не поддерживается")
            }
            if (nextStepCode != null) {
                // редирект на событие TelegramStepMessageEvent
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = nextStepCode
                    )
                )
            }
        }
    }

    @Bean
    @Lazy
    // Бин поступления сообщения от пользователя
    fun messageBean(): Message = Message()

    @Bean
    @Lazy
    // Бин отправки сообщения ботом
    fun stepMessageBean(): StepMessage = StepMessage()

    @Bean
    @Lazy
    // Бин, который срабатывает в момент клика по кнопке
    fun callbackMessageBean(): CallbackMessage = CallbackMessage()

}