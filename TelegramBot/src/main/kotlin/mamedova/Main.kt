package mamedova

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException

fun main() {
    ApiContextInitializer.init()

    val telegramBotsApi = TelegramBotsApi()

    try {
        telegramBotsApi.registerBot(TimerBot())
    } catch (e : TelegramApiRequestException) {
        e.printStackTrace()
    }
}