package cc.abro.telegramgamebot.db.repository;

import cc.abro.telegramgamebot.db.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {

}
