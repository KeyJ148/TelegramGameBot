package cc.abro.telegramgamebot.telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public record TelegramResponse(String textResponse, ReplyKeyboard keyboardResponse) {}
