package cc.abro.telegramgamebot.telegram;

import cc.abro.telegramgamebot.services.TelegramReceiveMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

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
        String receivedText = null;
        User sender = null;
        Chat chat = null;
        if (update.hasMessage()) {
            receivedText = update.getMessage().getText();
            sender = update.getMessage().getFrom();
            chat = update.getMessage().getChat();
        } else if (update.hasCallbackQuery()) {
            receivedText = update.getCallbackQuery().getData();
            sender = update.getCallbackQuery().getFrom();
            chat = update.getCallbackQuery().getMessage().getChat();
        }

        if (receivedText == null) {
            log.error("Not found request text in received update: " + update);
            return;
        }
        if (sender == null) {
            log.error("Not found sender info in received update: " + update);
            return;
        }
        if (chat == null) {
            log.error("Not found chat info in received update: " + update);
            return;
        }

        List<BotApiMethod<?>> executeMethods = telegramReceiveMessagesService.receiveMessage(receivedText, sender, chat);
        for (BotApiMethod<?> method : executeMethods) {
            try {
                execute(method);
            } catch (TelegramApiException e) {
                log.error("Error while executing response", e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getUsername();
    }
}