package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.db.entity.Account;

public interface GameStateService {

    GameStateResponse processMessage(Account account, String message);
    GameState getProcessedGameState();
}
