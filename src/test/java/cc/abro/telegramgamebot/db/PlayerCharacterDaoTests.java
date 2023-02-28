package cc.abro.telegramgamebot.db;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.db.entity.Character;
import cc.abro.telegramgamebot.db.entity.Player;
import cc.abro.telegramgamebot.db.repository.AccountRepository;
import cc.abro.telegramgamebot.db.repository.CharacterRepository;
import cc.abro.telegramgamebot.db.repository.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerCharacterDaoTests extends InMemoryDbTests {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private CharacterRepository characterRepository;

    @BeforeEach
    public void createAndSave() {
        playerRepository.save(getPlayer());
        accountRepository.save(getAccount());
        characterRepository.saveAll(getCharacters());
    }

    @Test
    public void createAndDeleteTest() {
        Account account = getAccount();
        Player player = getPlayer();
        Character character1 = getCharacters().get(0);
        Character character2 = getCharacters().get(1);

        Assertions.assertEquals(account,
                accountRepository.getReferenceById(account.getId()));
        Assertions.assertEquals(player,
                playerRepository.getReferenceById(player.getId()));
        Assertions.assertEquals(Set.of(character1, character2),
                new HashSet<>(characterRepository.findAllById(List.of(character1.getId(), character2.getId()))));

        characterRepository.delete(character1);
        characterRepository.delete(character2);
        accountRepository.delete(account);
        playerRepository.delete(player);

        Assertions.assertFalse(characterRepository.existsById(character1.getId()));
        Assertions.assertFalse(characterRepository.existsById(character2.getId()));
        Assertions.assertFalse(accountRepository.existsById(account.getId()));
        Assertions.assertFalse(playerRepository.existsById(player.getId()));
    }

    @Test
    public void deletePlayerBeforeAccountFailedTest() {
        characterRepository.deleteAll(getCharacters());
        playerRepository.delete(getPlayer());

        Assertions.assertThrowsExactly(DataIntegrityViolationException.class,
                () -> playerRepository.existsById(getPlayer().getId()));
    }

    @Test
    public void deletePlayerBeforeCharactersFailedTest() {
        accountRepository.delete(getAccount());
        playerRepository.delete(getPlayer());

        Assertions.assertThrowsExactly(DataIntegrityViolationException.class,
                () -> playerRepository.existsById(getPlayer().getId()));
    }
}
