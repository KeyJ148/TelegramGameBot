package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import org.springframework.stereotype.Service;

@Service
public class MainMenuStateService implements GameStateService {

    private final MainMenuViewService mainMenuViewService;

    public MainMenuStateService(MainMenuViewService mainMenuViewService) {
        this.mainMenuViewService = mainMenuViewService;
    }

    @Override
    public GameStateResponse processMessage(Account account, String message) {
        if (message.equals("Персонаж") || message.equals("Персонажи")) { //TODO вынести все в константы / i18n (prefix button.*)
            return new GameStateResponse(GameState.MAIN_MENU_CHARACTERS,
                    mainMenuViewService.getMainMenuCharactersView(account));
        }
        if (message.equals("Зоны охоты")) {
            return new GameStateResponse(GameState.MAIN_MENU_ZONES,
                    mainMenuViewService.getMainMenuZonesView(account));
        }
        return null;
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.MAIN_MENU;
    }
}