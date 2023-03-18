package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class StartStateService implements GameStateService {

    @Override
    public GameStateResponse processMessage(Account account, String message) {
        return new GameStateResponse(GameState.NEW_PLAYER,
                "Здравствуйте! Для начала создайте персонажа.\nВведите имя персонажа:");
    }

    @Override
    public GameState getProcessedGameState() {
        return GameState.START;
    }
}
