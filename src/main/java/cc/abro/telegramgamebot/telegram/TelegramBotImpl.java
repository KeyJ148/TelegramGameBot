package cc.abro.telegramgamebot.telegram;

import cc.abro.telegramgamebot.services.TelegramReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@Slf4j
public class TelegramBotImpl extends TelegramLongPollingBot implements TelegramBot {

    private final TelegramBotConfig telegramBotConfig;
    private final TelegramReceiveService telegramReceiveService;

    public TelegramBotImpl(DefaultBotOptions defaultBotOptions,
                           TelegramBotConfig telegramBotConfig,
                           TelegramReceiveService telegramReceiveService) {
        super(defaultBotOptions, telegramBotConfig.getToken());
        this.telegramBotConfig = telegramBotConfig;
        this.telegramReceiveService = telegramReceiveService;
    }

    public void init() {
        try {
            execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public InlineKeyboardMarkup inlineMarkup() {
        InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
        InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");
        InlineKeyboardButton SETTINGS_BUTTON = new InlineKeyboardButton("Settings");
        START_BUTTON.setCallbackData("/start");
        HELP_BUTTON.setCallbackData("/help");
        SETTINGS_BUTTON.setCallbackData("/settings");

        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON, HELP_BUTTON, SETTINGS_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info")
    );

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

        telegramReceiveService.receiveMessage(receivedText, sender);


        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());
        message.setText("Hi! " + receivedText);
        message.setReplyMarkup(inlineMarkup());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getUsername();
    }
}