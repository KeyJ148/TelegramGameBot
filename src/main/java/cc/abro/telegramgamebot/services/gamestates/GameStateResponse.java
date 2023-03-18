package cc.abro.telegramgamebot.services.gamestates;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public record GameStateResponse(GameState newGameState, String textResponse, ReplyKeyboard keyboardResponse) {

    public GameStateResponse(GameState newGameState, String textResponse) {
        this(newGameState, textResponse, null);
    }
}
