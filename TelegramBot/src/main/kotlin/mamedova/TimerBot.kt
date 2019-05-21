package mamedova

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class TimerBot : TelegramLongPollingBot() {

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    override fun onUpdateReceived(update: Update?) {
        val message = update?.message?.text
        val chatId = update?.message?.chatId.toString()
        sendMsg(chatId, message!!)
    }

    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId - id чата.
     * @param message - строка, которую необходимо обработать.
     */
    @Synchronized
    fun sendMsg(chatId: String, message: String) {
        val sendMessage = SendMessage()
        sendMessage.enableMarkdown(true)
        sendMessage.chatId = chatId


        try {
            if (message == "/start") {
                sayHello(sendMessage)
            } else {
                timer(sendMessage, message)
            }
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    /**
     * Метод вывода привествия при вызове команды /start
     */
    private fun sayHello(sendMessage: SendMessage) {
        sendMessage.text = "Приветик! Напиши, сколько мне нужно подождать :)"
        execute(sendMessage)
    }

    /**
     * Основной метод таймера
     * @param message Строка, которую необходимо обработать.
     */
    private fun timer(sendMessage: SendMessage, message: String) {
        var message = message.toLowerCase()
        val time = getTime(message)

        message = Regex("""\D+""").replace(message, "")

        if (message == "") {
            sendMessage.text = "Я не понимаю сколько надо ждать :("
            execute(sendMessage)
        } else {
            execute(wait(sendMessage, message, time))
        }
    }

    /**
     * Метод, который определяет единицу времени.
     * @param message - строка, которую необходимо обработать.
     * @return единицу времени.
     */
    private fun getTime(message: String): String {
        return when {
            Regex("секунд|секунду|секунды").containsMatchIn(message) -> when {
                message.contains("секунду") -> "секунда"
                message.contains("секунды") -> "секунды"
                else -> "секунд"
            }
            Regex("минут|минуту|минуты").containsMatchIn(message) -> when {
                message.contains("минуту") -> "минута"
                message.contains("минуты") -> "минуты"
                else -> "минут"
            }
            Regex("часов|час|часа").containsMatchIn(message) -> when {
                message.contains("часов") -> "часов"
                message.contains("часа") -> "часа"
                else -> "час"
            }
            Regex("дней|день|дня").containsMatchIn(message) -> when {
                message.contains("дней") -> "дней"
                message.contains("день") -> "день"
                else -> "дня"
            }
            else -> ""
        }
    }

    /**
     * Метод, который определяет единицу времени.
     * @param message - строка, которая содержит число (секунд, минут, часов или дней), которое нужно подождать
     * @param time - единица времени.
     */
    private fun wait(sendMessage: SendMessage, message: String, time: String): SendMessage {
        if (time == "секунд" || time == "секунда" || time == "секунды") {
            Thread.sleep(message.toLong() * 1000)

            when (time) {
                "секунда" -> sendMessage.text = "Прошла $message $time!"
                else -> sendMessage.text = "Прошло $message $time!"
            }

        } else if (time == "минут" || time == "минута" || time == "минуты") {
            Thread.sleep(message.toLong() * 60000)

            when (time) {
                "минута" -> sendMessage.text = "Прошла $message $time!"
                else -> sendMessage.text = "Прошло $message $time!"
            }

        } else if (time == "часов" || time == "час" || time == "часа") {
            Thread.sleep(message.toLong() * 3600000)

            when (time) {
                "час" -> sendMessage.text = "Прошел $message $time!"
                else -> sendMessage.text = "Прошло $message $time!"
            }

        } else if (time == "дней" || time == "день" || time == "дня") {
            Thread.sleep(message.toLong() * 86400000)

            when (time) {
                "день" -> sendMessage.text = "Прошел $message $time!"
                else -> sendMessage.text = "Прошло $message $time!"
            }

        } else {
            sendMessage.text = "Я не понимаю сколько надо ждать :("
        }

        return sendMessage
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    override fun getBotToken(): String {
        return "815025785:AAFGjGgvMQFndhww4Mz4LYTB3obWZb07Zbk"
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    override fun getBotUsername(): String {
        return "BotTimerBot"
    }

}
