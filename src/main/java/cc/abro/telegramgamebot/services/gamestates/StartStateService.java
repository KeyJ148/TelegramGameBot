package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.view.StartViewService;
import org.springframework.stereotype.Service;

@Service
public class StartStateService implements GameStateService {

    private final StartViewService startViewService;

    public StartStateService(StartViewService startViewService) {
        this.startViewService = startViewService;
    }

    @Override
    public GameStateResponse processMessage(Account account, String message) {
        return new GameStateResponse(GameState.NEW_PLAYER, startViewService.getStartView(account));
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.START;
    }
}
