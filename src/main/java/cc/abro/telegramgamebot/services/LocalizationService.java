package cc.abro.telegramgamebot.services;

import cc.abro.telegramgamebot.db.entity.Account;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
public class LocalizationService {

    private final MessageSource messageSource;

    public LocalizationService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getText(Account account, String code, Objects... args) {
        return messageSource.getMessage("texts." + code, args, Locale.forLanguageTag(account.getLocale()));
    }

    public String getButton(Account account, String code, Objects... args) {
        return messageSource.getMessage("buttons." + code, args, Locale.forLanguageTag(account.getLocale()));
    }
}
