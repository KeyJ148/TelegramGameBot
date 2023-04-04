package cc.abro.telegramgamebot.db;

import cc.abro.telegramgamebot.DefaultTests;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class InMemoryDbTests extends DefaultTests {
}
