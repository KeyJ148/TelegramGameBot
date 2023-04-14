package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.CharacterService;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import org.springframework.stereotype.Service;

@Service
public class NewPlayerStateService implements GameStateService {

    private final CharacterService characterService;
    private final MainMenuViewService mainMenuViewService;
    private final LocalizationService localizationService;

    public NewPlayerStateService(CharacterService characterService,
                                 MainMenuViewService mainMenuViewService,
                                 LocalizationService localizationService) {
        this.characterService = characterService;
        this.mainMenuViewService = mainMenuViewService;
        this.localizationService = localizationService;
    }

    @Override
    public GameStateResponse processMessage(Account account, String nickname) {
        if (nickname.isEmpty() || nickname.isBlank()) {
            return new GameStateResponse(GameState.NEW_PLAYER,
                    localizationService.getText(account, "new_player.error.empty"));
        }
        if (nickname.length() > 30) {
            return new GameStateResponse(GameState.NEW_PLAYER,
                    localizationService.getText(account, "new_player.error.too_long"));
        }
        if (nickname.contains("/")) {
            return new GameStateResponse(GameState.NEW_PLAYER,
                    localizationService.getText(account, "new_player.error.slash"));
        }

        characterService.createMainCharacter(account.getPlayer(), nickname);
        return new GameStateResponse(GameState.MAIN_MENU,
                localizationService.getText(account, "new_player.success", nickname),
                mainMenuViewService.getMainMenuView(account));
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.NEW_PLAYER;
    }
}
