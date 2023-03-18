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
        if (!serviceByGameState.containsKey(currentGameState)) {
            log.error("Not found processor for GameState: " + currentGameState);
            accountService.setGameState(account, GameState.MAIN_MENU);
            return new TelegramResponse("Произошла неизвестная ошибка. Вы возвращены в меню.",
                    mainMenuViewService.createMainMenuKeyboard(account));
        }

        GameStateResponse gameStateResponse = serviceByGameState.get(currentGameState).processMessage(account, message);
        accountService.setGameState(account, gameStateResponse.newGameState());
        return new TelegramResponse(gameStateResponse.textResponse(), gameStateResponse.keyboardResponse());
    }
}
