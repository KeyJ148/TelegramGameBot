package cc.abro.telegramgamebot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfiguration botConfiguration;

    public TelegramBot(DefaultBotOptions defaultBotOptions, BotConfiguration botConfiguration) {
        super(defaultBotOptions, botConfiguration.getToken());
        this.botConfiguration = botConfiguration;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            System.out.println(update);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getUsername();
    }
}
