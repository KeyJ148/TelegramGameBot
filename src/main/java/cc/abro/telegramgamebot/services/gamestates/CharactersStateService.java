package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.entity.CharacterService;
import cc.abro.telegramgamebot.services.view.CharacterViewService;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import org.springframework.stereotype.Service;

@Service
public class CharactersStateService implements GameStateService {

    private final MainMenuViewService mainMenuViewService;
    private final LocalizationService localizationService;
    private final CharacterViewService characterViewService;
    private final CharacterService characterService;

    public CharactersStateService(MainMenuViewService mainMenuViewService,
                                  LocalizationService localizationService,
                                  CharacterViewService characterViewService,
                                  CharacterService characterService) {
        this.mainMenuViewService = mainMenuViewService;
        this.localizationService = localizationService;
        this.characterViewService = characterViewService;
        this.characterService = characterService;
    }

    @Override
    public GameStateResponse processMessage(Account account, String message) {
        if (message.equals(localizationService.getButton(account, "back"))) {
            if (characterService.getCountCharacters(account.getPlayer()) == 1) {
                return new GameStateResponse(GameState.MAIN_MENU, mainMenuViewService.getMainMenuView(account,
                        characterService.getCountCharacters(account.getPlayer())));
            } else {
                return new GameStateResponse(GameState.MAIN_MENU_CHARACTERS,
                        mainMenuViewService.getMainMenuCharactersView(account,
                                characterService.getAllCharacters(account.getPlayer())));
            }
        }
        return null;
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.CHARACTER;
    }
}
