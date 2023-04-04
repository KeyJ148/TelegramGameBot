package cc.abro.telegramgamebot.services;

import cc.abro.telegramgamebot.DefaultTests;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public abstract class DefaultServiceTests extends DefaultTests {
}
