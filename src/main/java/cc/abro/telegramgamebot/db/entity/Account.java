package cc.abro.telegramgamebot.db.entity;

import cc.abro.telegramgamebot.services.gamestates.GameState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "index_telegramUserId", columnList = "telegramUserId", unique = true))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    private long telegramUserId;
    private GameState state;
    private String locale;

    @OneToOne(optional = false)
    private Player player;
}