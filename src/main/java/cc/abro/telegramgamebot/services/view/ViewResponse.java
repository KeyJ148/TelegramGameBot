package cc.abro.telegramgamebot.services.view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public record ViewResponse(String textResponse, ReplyKeyboard keyboardResponse) {

    public ViewResponse(String textResponse) {
        this(textResponse, null);
    }
}
