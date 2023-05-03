package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.model.Race;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.entity.CharacterService;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import cc.abro.telegramgamebot.services.view.StartViewService;
import cc.abro.telegramgamebot.telegram.TelegramBotInitializer;
import org.springframework.stereotype.Service;

@Service
public class NewPlayerStateService implements GameStateService {

    private final CharacterService characterService;
    private final MainMenuViewService mainMenuViewService;
    private final LocalizationService localizationService;
    private final StartViewService startViewService;

    public NewPlayerStateService(CharacterService characterService,
                                 MainMenuViewService mainMenuViewService,
                                 LocalizationService localizationService,
                                 StartViewService startViewService) {
        this.characterService = characterService;
        this.mainMenuViewService = mainMenuViewService;
        this.localizationService = localizationService;
        this.startViewService = startViewService;
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
        if (nickname.equals(TelegramBotInitializer.COMMAND_START)) {
            return new GameStateResponse(GameState.NEW_PLAYER,
                    startViewService.getStartView(account));
        }
        if (nickname.contains("/")) {
            return new GameStateResponse(GameState.NEW_PLAYER,
                    localizationService.getText(account, "new_player.error.slash"));
        }
        if (!nickname.matches("^[a-zA-Z0-9.]+$")) {
            return new GameStateResponse(GameState.NEW_PLAYER,
                    localizationService.getText(account, "new_player.error.latin_only"));
        }

        characterService.createMainCharacter(account.getPlayer(), nickname, Race.HUMAN);
        return new GameStateResponse(GameState.MAIN_MENU,
                localizationService.getText(account, "new_player.success", nickname),
                mainMenuViewService.getMainMenuView(account, characterService.getCountCharacters(account.getPlayer())));
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.NEW_PLAYER;
    }
}
