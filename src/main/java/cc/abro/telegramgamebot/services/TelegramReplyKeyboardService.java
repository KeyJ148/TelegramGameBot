package cc.abro.telegramgamebot.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;

@Service
public class TelegramReplyKeyboardService {

    public InlineKeyboardMarkup createInlineReplyKeyboard(List<List<String>> buttonRows) {
        List<List<InlineKeyboardButton>> keyboardRows = buttonRows.stream()
                .map(buttonRow -> buttonRow.stream()
                        .map(buttonText -> {
                            int splitterPosition = buttonText.indexOf(' ');
                            String command = buttonText.substring(0, splitterPosition);
                            String text = buttonText.substring(splitterPosition  + 1);
                            InlineKeyboardButton keyboardButton = new InlineKeyboardButton(text);
                            keyboardButton.setCallbackData(command);
                            return keyboardButton;
                        })
                        .toList())
                .toList();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup createReplyKeyboard(List<List<String>> buttonRows) {
        List<KeyboardRow> keyboardRows = buttonRows.stream()
                .map(buttonRow -> buttonRow.stream()
                        .map(KeyboardButton::new)
                        .toList())
                .map(KeyboardRow::new)
                .toList();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup createReplyKeyboardHorizontal(String... buttons) {
        return createReplyKeyboard(List.of(Arrays.stream(buttons).toList()));
    }

    public ReplyKeyboardMarkup createReplyKeyboardVertical(String... buttons) {
        return createReplyKeyboard(Arrays.stream(buttons)
                .map(List::of)
                .toList()
        );
    }

    public ReplyKeyboardMarkup createReplyKeyboard(List<List<String>> buttonRows, String placeholder) {
        ReplyKeyboardMarkup replyKeyboardMarkup = createReplyKeyboard(buttonRows);
        replyKeyboardMarkup.setInputFieldPlaceholder(placeholder);

        return replyKeyboardMarkup;
    }
}
