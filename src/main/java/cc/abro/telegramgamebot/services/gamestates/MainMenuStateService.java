package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import org.springframework.stereotype.Service;

@Service
public class MainMenuStateService implements GameStateService {

    private final MainMenuViewService mainMenuViewService;
    private final LocalizationService localizationService;

    public MainMenuStateService(MainMenuViewService mainMenuViewService,
                                LocalizationService localizationService) {
        this.mainMenuViewService = mainMenuViewService;
        this.localizationService = localizationService;
    }

    @Override
    public GameStateResponse processMessage(Account account, String message) {
        if (message.equals(localizationService.getButton(account, "character")) ||
                message.equals(localizationService.getButton(account, "characters"))) {
            return new GameStateResponse(GameState.MAIN_MENU_CHARACTERS,
                    mainMenuViewService.getMainMenuCharactersView(account));
        }
        if (message.equals(localizationService.getButton(account, "zones"))) {
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