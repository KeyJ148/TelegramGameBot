package cc.abro.telegramgamebot.telegram;

import org.telegram.telegrambots.meta.generics.LongPollingBot;

public interface TelegramBot extends LongPollingBot {
    void init();
}
