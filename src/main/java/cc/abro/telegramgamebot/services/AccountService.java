package cc.abro.telegramgamebot.services;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.db.entity.Player;
import cc.abro.telegramgamebot.db.repository.AccountRepository;
import cc.abro.telegramgamebot.db.repository.PlayerRepository;
import cc.abro.telegramgamebot.services.gamestates.GameState;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final static GameState DEFAULT_GAME_STATE = GameState.NEW_PLAYER;
    private final static String DEFAULT_LOCALE = "ru";

    private final AccountRepository accountRepository;
    private final PlayerRepository playerRepository;

    public AccountService(AccountRepository accountRepository, PlayerRepository playerRepository) {
        this.accountRepository = accountRepository;
        this.playerRepository = playerRepository;
    }

    public Account getOrCreateAccount(long telegramUserId) {
        Account account = accountRepository.getByTelegramUserId(telegramUserId);
        if (account == null) {
            Player player = Player.builder()
                    .build();

            account = Account.builder()
                    .telegramUserId(telegramUserId)
                    .locale(DEFAULT_LOCALE)
                    .state(DEFAULT_GAME_STATE)
                    .player(player)
                    .build();

            playerRepository.save(player);
            accountRepository.save(account);
        }
        return account;
    }

    public void setGameState(Account account, GameState newGameState) {
        account.setState(newGameState);
        accountRepository.save(account);
    }
}
