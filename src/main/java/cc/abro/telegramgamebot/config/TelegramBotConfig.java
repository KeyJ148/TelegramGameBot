package cc.abro.telegramgamebot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Data
@Configuration
public class TelegramBotConfig {

    @Value("${telegram.bot.token}")
    private String token;
    @Value("${telegram.bot.username}")
    private String username;

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        defaultBotOptions.setGetUpdatesTimeout(0);
        return defaultBotOptions;
    }
}
