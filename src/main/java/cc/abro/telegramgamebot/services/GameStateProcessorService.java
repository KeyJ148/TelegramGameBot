package cc.abro.telegramgamebot.services;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.services.gamestates.GameState;
import cc.abro.telegramgamebot.services.gamestates.GameStateResponse;
import cc.abro.telegramgamebot.services.gamestates.GameStateService;
import cc.abro.telegramgamebot.services.view.MainMenuViewService;
import cc.abro.telegramgamebot.telegram.TelegramResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class GameStateProcessorService {

    private final ApplicationContext context;
    private final AccountService accountService;
    private final MainMenuViewService mainMenuViewService;

    private final Map<GameState, GameStateService> serviceByGameState = new HashMap<>();

    public GameStateProcessorService(ApplicationContext context,
                                     AccountService accountService,
                                     MainMenuViewService mainMenuViewService) {
        this.context = context;
        this.accountService = accountService;
        this.mainMenuViewService = mainMenuViewService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        context.getBeansWithAnnotation(Service.class).values().stream()
                .filter(service -> service instanceof GameStateService)
                .map(service -> (GameStateService) service)
                .forEach(service -> serviceByGameState.put(service.getProcessedGameState(), service));
    }

    public TelegramResponse processMessage(Account account, String message) {
        GameState currentGameState = account.getState();

        GameStateResponse gameStateResponse;
        try {
            gameStateResponse = serviceByGameState.get(currentGameState).processMessage(account, message);
        } catch (RuntimeException e) {
            log.error("Exception while process GameState: " + currentGameState + ", Account: " + account.getId(), e);
            gameStateResponse = new GameStateResponse(GameState.MAIN_MENU, "Произошла неизвестная ошибка.",
                    mainMenuViewService.getMainMenuCharactersView(account));
        }
        if (gameStateResponse == null) {
            return new TelegramResponse("Недопустимый ввод", null);
        }

        accountService.setGameState(account, gameStateResponse.newGameState());
        return new TelegramResponse(gameStateResponse.viewResponse().textResponse(),
                gameStateResponse.viewResponse().keyboardResponse());
    }
}
