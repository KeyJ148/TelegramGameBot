package cc.abro.telegramgamebot.services.view;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.db.entity.Character;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.TelegramReplyKeyboardService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MainMenuViewService {

    private final LocalizationService localizationService;
    private final TelegramReplyKeyboardService telegramReplyKeyboardService;

    public MainMenuViewService(LocalizationService localizationService,
                               TelegramReplyKeyboardService telegramReplyKeyboardService) {
        this.localizationService = localizationService;
        this.telegramReplyKeyboardService = telegramReplyKeyboardService;
    }

    public ViewResponse getMainMenuView(Account account, int countCharacters) {
        String charactersButton = countCharacters == 1 ?
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

    public ViewResponse getMainMenuCharactersView(Account account, List<Character> characters) {
        String textResponse = localizationService.getView(account, "character_list");

        List<String> charactersNames = characters.stream()
                .sorted((c1, c2) ->
                        Comparator.comparing(Character::isMainCharacter)
                        .thenComparing(Character::getName)
                        .compare(c1, c2))
                .map(Character::getName)
                .toList();
        List<String> buttons = new ArrayList<>(charactersNames);
        buttons.add(localizationService.getButton(account, "back"));
        ReplyKeyboard keyboardResponse = telegramReplyKeyboardService.createReplyKeyboardHorizontal(buttons);

        return new ViewResponse(textResponse, keyboardResponse);
    }

    public ViewResponse getMainMenuZonesView(Account account) {
        String textResponse = localizationService.getView(account, "zones");


        ReplyKeyboard keyboardResponse = telegramReplyKeyboardService.createReplyKeyboardHorizontal(
                localizationService.getButton(account, "zones.new"),
                localizationService.getButton(account, "back"));

        return new ViewResponse(textResponse, keyboardResponse);
    }
}
