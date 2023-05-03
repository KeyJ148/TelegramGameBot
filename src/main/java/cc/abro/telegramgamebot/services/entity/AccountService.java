package cc.abro.telegramgamebot.services.entity;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.db.entity.Player;
import cc.abro.telegramgamebot.db.entity.Zone;
import cc.abro.telegramgamebot.db.repository.AccountRepository;
import cc.abro.telegramgamebot.db.repository.PlayerRepository;
import cc.abro.telegramgamebot.db.repository.ZoneRepository;
import cc.abro.telegramgamebot.services.gamestates.GameState;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final static GameState DEFAULT_GAME_STATE = GameState.START;
    private final static String DEFAULT_LOCALE = "ru";

    private final AccountRepository accountRepository;
    private final PlayerRepository playerRepository;
    private final ZoneRepository zoneRepository;

    public AccountService(AccountRepository accountRepository,
                          PlayerRepository playerRepository,
                          ZoneRepository zoneRepository) {
        this.accountRepository = accountRepository;
        this.playerRepository = playerRepository;
        this.zoneRepository = zoneRepository;
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

            Zone zone = Zone.builder()
                    .difficultInEnergy(0)
                    .player(player)
                    .build();

            playerRepository.save(player);
            zoneRepository.save(zone);
            accountRepository.save(account);
        }
        return account;
    }

    public void setGameState(Account account, GameState newGameState) {
        account.setState(newGameState);
        accountRepository.save(account);
    }
}
