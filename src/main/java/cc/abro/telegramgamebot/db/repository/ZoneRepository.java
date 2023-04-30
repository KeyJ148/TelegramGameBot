package cc.abro.telegramgamebot.db.repository;

import cc.abro.telegramgamebot.db.entity.Player;
import cc.abro.telegramgamebot.db.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, UUID> {

    List<Zone> getZonesByPlayer(Player player);
}