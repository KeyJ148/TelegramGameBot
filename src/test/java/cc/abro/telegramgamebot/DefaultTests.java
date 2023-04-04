package cc.abro.telegramgamebot;

import cc.abro.telegramgamebot.db.entity.Account;
import cc.abro.telegramgamebot.db.entity.Character;
import cc.abro.telegramgamebot.db.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
public abstract class DefaultTests {

    protected static final long TELEGRAM_USER_ID = 1;
    protected static final String LOCALE = "ru";

    private Account account;
    private Player player;
    private List<Character> characters;

    @BeforeEach
    protected void initTestData() {
        player = Player.builder().build();

        account = Account.builder()
                .telegramUserId(TELEGRAM_USER_ID)
                .locale(LOCALE)
                .player(player)
                .build();

        characters = new ArrayList<>();
        characters.add(Character.builder()
                .player(player)
                .build());
        characters.add(Character.builder()
                .player(player)
                .build());
    }

    public Account getAccount() {
        return account;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}
