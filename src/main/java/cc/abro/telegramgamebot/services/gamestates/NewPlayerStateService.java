package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.CharacterService;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import org.springframework.stereotype.Service;

@Service
public class NewPlayerStateService implements GameStateService {

    private final CharacterService characterService;
    private final StartStateService startStateService;
    private final MainMenuViewService mainMenuViewService;

    public NewPlayerStateService(CharacterService characterService, StartStateService startStateService, MainMenuViewService mainMenuViewService) {
        this.characterService = characterService;
        this.startStateService = startStateService;
        this.mainMenuViewService = mainMenuViewService;
    }

    @Override
    public GameStateResponse processMessage(Account account, String message) {
        if (message.equals("/start") || message.equals("/settings") || message.equals("/help")) {
            return startStateService.processMessage(account, message);
            //TODO Сохранять в БД последнее сообщение и для каждого стейта возвращать список комманд (или лямбда / функция), которая проверит, что надо заново вывести последнее сообщение, т.к. был введен недопустимый и неожиданный ввод
        }
        if (message.isEmpty() || message.isBlank()) {
            return new GameStateResponse(GameState.NEW_PLAYER, "Имя персонажа не может быть пустым.");
        }
        if (message.length() > 30) {
            return new GameStateResponse(GameState.NEW_PLAYER, "Имя персонажа не может быть больше 30 символов.");
        }
        if (message.contains("/")) {
            return new GameStateResponse(GameState.NEW_PLAYER, "Имя персонажа не может содержать слэш.");
        }

        characterService.createMainCharacter(account.getPlayer(), message);
        return new GameStateResponse(GameState.MAIN_MENU,
                "Поздравляю! Вы создали персонажа с именем " + message + ".\n\nВы в главном меню.",
                mainMenuViewService.createMainMenuKeyboard(account));
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.NEW_PLAYER;
    }
}
