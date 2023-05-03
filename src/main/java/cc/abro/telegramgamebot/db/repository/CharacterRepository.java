package cc.abro.telegramgamebot.db.repository;

import cc.abro.telegramgamebot.db.entity.Character;
import cc.abro.telegramgamebot.db.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CharacterRepository extends JpaRepository<Character, UUID> {

    List<Character> getCharactersByPlayer(Player player);
    int countCharactersByPlayer(Player player);
    Character getCharacterByPlayerAndIsMainCharacterIsTrue(Player player);

    default Character getMainCharacter(Player player) {
        return getCharacterByPlayerAndIsMainCharacterIsTrue(player);
    }
}