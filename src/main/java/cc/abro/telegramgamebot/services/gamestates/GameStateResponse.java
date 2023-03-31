package cc.abro.telegramgamebot.services.gamestates;

import cc.abro.telegramgamebot.services.view.ViewResponse;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public record GameStateResponse(GameState newGameState, ViewResponse viewResponse) {

    public GameStateResponse(GameState newGameState, String textResponse) {
        this(newGameState, new ViewResponse(textResponse));
    }

    public GameStateResponse(GameState newGameState, String textResponse, ReplyKeyboard keyboardResponse) {
        this(newGameState, new ViewResponse(textResponse, keyboardResponse));
    }

    public GameStateResponse(GameState newGameState, String textResponse, ViewResponse viewResponse) {
        this(newGameState, mergeTexts(textResponse, viewResponse.textResponse()),
                viewResponse.keyboardResponse());
    }

    private static String mergeTexts(String text1, String text2) {
        if (text1 == null && text2 != null) {
            return text2;
        }
        if (text1 != null && text2 == null) {
            return text1;
        }
        if (text1 != null && text2 != null) {
            return text1 + "\n\n" + text2;
        }
        return null;
    }
}
