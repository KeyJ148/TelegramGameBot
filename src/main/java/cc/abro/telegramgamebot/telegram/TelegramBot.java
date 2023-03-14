package cc.abro.telegramgamebot.telegram;

import cc.abro.telegramgamebot.services.TelegramReceiveMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramBotConfig telegramBotConfig;
    private final TelegramReceiveMessagesService telegramReceiveMessagesService;

    public TelegramBot(DefaultBotOptions defaultBotOptions,
                       TelegramBotConfig telegramBotConfig,
                       TelegramReceiveMessagesService telegramReceiveMessagesService) {
        super(defaultBotOptions, telegramBotConfig.getToken());
        this.telegramBotConfig = telegramBotConfig;
        this.telegramReceiveMessagesService = telegramReceiveMessagesService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        telegramReceiveMessagesService.receiveMessage(null, null); //TODO
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getUsername();
    }
}