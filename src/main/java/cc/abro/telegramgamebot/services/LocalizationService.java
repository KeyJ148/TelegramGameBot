package cc.abro.telegramgamebot.services;

import cc.abro.telegramgamebot.db.entity.Account;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationService {

    private final MessageSource messageSource;

    public LocalizationService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getText(Account account, String code, Object... args) {
        return messageSource.getMessage("text." + code, args, Locale.forLanguageTag(account.getLocale()));
    }

    public String getButton(Account account, String code, Object... args) {
        return messageSource.getMessage("button." + code, args, Locale.forLanguageTag(account.getLocale()));
    }

    public String getView(Account account, String code, Object... args) {
        return messageSource.getMessage("view." + code, args, Locale.forLanguageTag(account.getLocale()));
    }
}
