package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.entity.CharacterService;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import org.springframework.stereotype.Service;

@Service
public class MainMenuZonesStateService implements GameStateService {

    private final MainMenuViewService mainMenuViewService;
    private final LocalizationService localizationService;
    private final CharacterService characterService;

    public MainMenuZonesStateService(MainMenuViewService mainMenuViewService,
                                     LocalizationService localizationService,
                                     CharacterService characterService) {
        this.mainMenuViewService = mainMenuViewService;
        this.localizationService = localizationService;
        this.characterService = characterService;
    }

    @Override
    public GameStateResponse processMessage(Account account, String message) {
        if (message.equals(localizationService.getButton(account, "back"))) {
            return new GameStateResponse(GameState.MAIN_MENU, mainMenuViewService.getMainMenuView(account,
                    characterService.getCountCharacters(account.getPlayer())));
        }
        return null;
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.MAIN_MENU_ZONES;
    }
}
