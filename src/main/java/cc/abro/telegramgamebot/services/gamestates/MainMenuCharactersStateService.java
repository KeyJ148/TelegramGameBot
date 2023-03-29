package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import org.springframework.stereotype.Service;

@Service
public class MainMenuCharactersStateService implements GameStateService {

    private final MainMenuViewService mainMenuViewService;

    public MainMenuCharactersStateService(MainMenuViewService mainMenuViewService) {
        this.mainMenuViewService = mainMenuViewService;
    }

    @Override
    public GameStateResponse processMessage(Account account, String message) {
        if (message.equals("Назад")) {
            return new GameStateResponse(GameState.MAIN_MENU, mainMenuViewService.getMainMenuView(account));
        }
        return null;
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.MAIN_MENU_CHARACTERS;
    }
}
