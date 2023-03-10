package cc.abro.telegramgamebot.telegram;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.BotOptions;

@Component
@Primary
public class TelegramBotMock implements TelegramBot {

    @Override
    public void init() {
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public BotOptions getOptions() {
        return null;
    }

    @Override
    public void clearWebhook() {
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }
}
