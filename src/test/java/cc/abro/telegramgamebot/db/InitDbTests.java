package cc.abro.telegramgamebot.db;

import cc.abro.telegramgamebot.db.repository.AccountRepository;
import cc.abro.telegramgamebot.db.repository.CharacterRepository;
import cc.abro.telegramgamebot.db.repository.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InitDbTests extends DefaultDbTests {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private CharacterRepository characterRepository;

    @Test
    public void initAllTableTest() {
        Assertions.assertEquals(0, accountRepository.count());
        Assertions.assertEquals(0, playerRepository.count());
        Assertions.assertEquals(0, characterRepository.count());
    }
}
