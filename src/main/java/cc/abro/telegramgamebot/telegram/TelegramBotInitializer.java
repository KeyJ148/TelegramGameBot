package cc.abro.telegramgamebot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Slf4j
@Component
@Profile("prod")
public class TelegramBotInitializer {

    public final static String COMMAND_START = "/start";
    public final static String COMMAND_MENU = "/menu";
    public final static String COMMAND_HELP = "/help";
    public final static String COMMAND_SETTINGS = "/settings";

    private final static List<BotCommand> DEFAULT_COMMANDS = List.of(
            new BotCommand(COMMAND_START, "Game start"),
            new BotCommand(COMMAND_MENU, "Main menu"),
            new BotCommand(COMMAND_HELP, "Help"),
            new BotCommand(COMMAND_SETTINGS, "Settings")
    );

    private final TelegramBot telegramBot;

    public TelegramBotInitializer(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() throws TelegramApiException{
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
            telegramBot.execute(new SetMyCommands(DEFAULT_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
