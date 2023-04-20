package cc.abro.telegramgamebot.services.view;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.db.entity.Character;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.TelegramReplyKeyboardService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Service
public class CharacterViewService {

    private final LocalizationService localizationService;
    private final TelegramReplyKeyboardService telegramReplyKeyboardService;

    public CharacterViewService(LocalizationService localizationService,
                                TelegramReplyKeyboardService telegramReplyKeyboardService) {
        this.localizationService = localizationService;
        this.telegramReplyKeyboardService = telegramReplyKeyboardService;
    }

    public ViewResponse getCharacterView(Account account, Character character) {
        String textResponse = localizationService.getView(account, "character", character.getName());

        ReplyKeyboard keyboardResponse = telegramReplyKeyboardService.createReplyKeyboardHorizontal(
                localizationService.getButton(account, "back"));
        return new ViewResponse(textResponse, keyboardResponse);
    }
}
