package cc.abro.telegramgamebot.services;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.db.entity.Player;
import cc.abro.telegramgamebot.db.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getOrCreateAccount(long telegramUserId) {
        Account account = accountRepository.getByTelegramUserId(telegramUserId);
        if (account == null) {
            Player player = Player.builder()
                    .build();

            account = Account.builder()
                    .telegramUserId(telegramUserId)
                    .player(player)
                    .build();
            accountRepository.save(account);
        }
        return account;
    }
}
