package cc.abro.telegramgamebot.services;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.telegram.TelegramResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

@Slf4j
@Service
public class TelegramReceiveMessagesService {

    private final AccountService accountService;
    private final GameStateProcessorService gameStateProcessorService;

    public TelegramReceiveMessagesService(AccountService accountService,
                                          GameStateProcessorService gameStateProcessorService) {
        this.accountService = accountService;
        this.gameStateProcessorService = gameStateProcessorService;
    }

    public List<BotApiMethod<?>> receiveMessage(String receivedText, User sender, Chat chat) {
        log.info("[{}] Received message: {}", sender.getId(), receivedText);

        Account account = accountService.getOrCreateAccount(sender.getId());
        TelegramResponse telegramResponse = gameStateProcessorService.processMessage(account, receivedText);

        SendMessage responseMessage = new SendMessage();
        responseMessage.setChatId(chat.getId());
        responseMessage.setText(telegramResponse.textResponse());
        responseMessage.setParseMode(ParseMode.MARKDOWN);
        responseMessage.setReplyMarkup(telegramResponse.keyboardResponse());

        log.info("[{}] Send message: {}", sender.getId(), telegramResponse.textResponse());
        return List.of(responseMessage);
    }

}
