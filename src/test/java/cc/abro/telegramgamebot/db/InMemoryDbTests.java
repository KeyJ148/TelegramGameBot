package cc.abro.telegramgamebot.db;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@AutoConfigureTestDatabase(replace = Replace.ANY)
public abstract class InMemoryDbTests extends DefaultDbTests {
}
