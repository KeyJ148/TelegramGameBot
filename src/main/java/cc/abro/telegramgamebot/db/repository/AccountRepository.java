package cc.abro.telegramgamebot.db.repository;

import cc.abro.telegramgamebot.db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Account getByTelegramUserId(long telegramUserId);
}
