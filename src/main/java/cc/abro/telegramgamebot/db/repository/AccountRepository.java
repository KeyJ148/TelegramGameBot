package cc.abro.telegramgamebot.db.repository;

import cc.abro.telegramgamebot.db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Account getByTelegramUserId(long telegramUserId);
}
