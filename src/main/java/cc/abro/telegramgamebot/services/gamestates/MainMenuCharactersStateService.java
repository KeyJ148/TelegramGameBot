package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.db.entity.Character;
import cc.abro.telegramgamebot.services.CharacterService;
import cc.abro.telegramgamebot.services.LocalizationService;
import cc.abro.telegramgamebot.services.view.CharacterViewService;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainMenuCharactersStateService implements GameStateService {

    private final MainMenuViewService mainMenuViewService;
    private final LocalizationService localizationService;
    private final CharacterViewService characterViewService;
    private final CharacterService characterService;

    public MainMenuCharactersStateService(MainMenuViewService mainMenuViewService,
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
        Optional<Character> character = characterService.getAllCharacters(account.getPlayer()).stream()
                .filter(c -> c.getName().equalsIgnoreCase(message))
                .findFirst();
        if (character.isPresent()) {
            return new GameStateResponse(GameState.CHARACTER,
                    characterViewService.getCharacterView(account, character.get()));
        }

        if (message.equals(localizationService.getButton(account, "back"))) {
            return new GameStateResponse(GameState.MAIN_MENU, mainMenuViewService.getMainMenuView(account,
                    characterService.getCountCharacters(account.getPlayer())));
        }
        return null;
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.MAIN_MENU_CHARACTERS;
    }
}
