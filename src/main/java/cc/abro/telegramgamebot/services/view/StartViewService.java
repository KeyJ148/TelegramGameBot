package cc.abro.telegramgamebot.services.view;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.LocalizationService;
import org.springframework.stereotype.Service;

@Service
public class StartViewService {

    private final LocalizationService localizationService;

    public StartViewService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    public ViewResponse getStartView(Account account) {
        return new ViewResponse(localizationService.getView(account, "start"));
    }
}
