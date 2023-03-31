package cc.abro.telegramgamebot.services.view;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.CharacterService;
import cc.abro.telegramgamebot.services.TelegramReplyKeyboardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainMenuViewService {

    private final TelegramReplyKeyboardService telegramReplyKeyboardService;
    private final CharacterService characterService;

    public MainMenuViewService(TelegramReplyKeyboardService telegramReplyKeyboardService,
                               CharacterService characterService) {
        this.telegramReplyKeyboardService = telegramReplyKeyboardService;
        this.characterService = characterService;
    }

    public ViewResponse getMainMenuView(Account account) {
        List<String> buttons = List.of(
                characterService.getCountCharacters(account.getPlayer()) == 1 ? "Персонаж" : "Персонажи",
                "Зоны охоты"
        );

        return new ViewResponse("Вы в главном меню",
                telegramReplyKeyboardService.createReplyKeyboardVertical(buttons));
    }

    public ViewResponse getMainMenuCharactersView(Account account) {
        return new ViewResponse("Список персонажей",
                telegramReplyKeyboardService.createReplyKeyboardHorizontal("Назад"));
    }

    public ViewResponse getMainMenuZonesView(Account account) {
        return new ViewResponse("Зоны охоты",
                telegramReplyKeyboardService.createReplyKeyboardHorizontal("Назад"));
    }
}
