package cc.abro.telegramgamebot.services.view;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.CharacterService;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.TelegramReplyKeyboardService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;

@Service
public class MainMenuViewService {

    private final LocalizationService localizationService;
    private final TelegramReplyKeyboardService telegramReplyKeyboardService;
    private final CharacterService characterService;

    public MainMenuViewService(LocalizationService localizationService,
                               TelegramReplyKeyboardService telegramReplyKeyboardService,
                               CharacterService characterService) {
        this.localizationService = localizationService;
        this.telegramReplyKeyboardService = telegramReplyKeyboardService;
        this.characterService = characterService;
    }

    public ViewResponse getMainMenuView(Account account) {
        String charactersButton = characterService.getCountCharacters(account.getPlayer()) == 1 ?
                localizationService.getButton(account, "character") :
                localizationService.getButton(account, "characters");

        List<String> buttons = List.of(
                charactersButton,
                localizationService.getButton(account, "zones")
        );

        String textResponse = localizationService.getView(account, "main_menu");
        ReplyKeyboard keyboardResponse = telegramReplyKeyboardService.createReplyKeyboardVertical(buttons);
        return new ViewResponse(textResponse, keyboardResponse);
    }

    public ViewResponse getMainMenuCharactersView(Account account) {
        String textResponse = localizationService.getView(account, "character_list");
        ReplyKeyboard keyboardResponse = telegramReplyKeyboardService.createReplyKeyboardHorizontal(
                localizationService.getButton(account, "back"));
        return new ViewResponse(textResponse, keyboardResponse);
    }

    public ViewResponse getMainMenuZonesView(Account account) {
        String textResponse = localizationService.getView(account, "zones");
        ReplyKeyboard keyboardResponse = telegramReplyKeyboardService.createReplyKeyboardHorizontal(
                localizationService.getButton(account, "back"));
        return new ViewResponse(textResponse, keyboardResponse);
    }
}
