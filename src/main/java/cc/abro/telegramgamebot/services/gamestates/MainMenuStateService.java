package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.entity.CharacterService;
import cc.abro.telegramgamebot.services.view.CharacterViewService;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import org.springframework.stereotype.Service;

@Service
public class MainMenuStateService implements GameStateService {

    private final MainMenuViewService mainMenuViewService;
    private final CharacterViewService characterViewService;
    private final LocalizationService localizationService;
    private final CharacterService characterService;

    public MainMenuStateService(MainMenuViewService mainMenuViewService,
                                CharacterViewService characterViewService,
                                LocalizationService localizationService,
                                CharacterService characterService) {
        this.mainMenuViewService = mainMenuViewService;
        this.characterViewService = characterViewService;
        this.localizationService = localizationService;
        this.characterService = characterService;
    }

    @Override
    public GameStateResponse processMessage(Account account, String message) {
        if (message.equals(localizationService.getButton(account, "character"))) {
            return new GameStateResponse(GameState.CHARACTER,
                    characterViewService.getCharacterView(account,
                            characterService.getMainCharacter(account.getPlayer())));
        }
        if (message.equals(localizationService.getButton(account, "characters"))) {
            return new GameStateResponse(GameState.MAIN_MENU_CHARACTERS,
                    mainMenuViewService.getMainMenuCharactersView(account,
                            characterService.getAllCharacters(account.getPlayer())));
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