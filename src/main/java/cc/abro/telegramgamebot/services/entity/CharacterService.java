package cc.abro.telegramgamebot.services.entity;

import cc.abro.telegramgamebot.db.entity.Character;
import cc.abro.telegramgamebot.db.entity.Player;
import cc.abro.telegramgamebot.db.repository.CharacterRepository;
import cc.abro.telegramgamebot.model.Race;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public void createMainCharacter(Player player, String nickname, Race race) {
        Character character = Character.builder()
                .player(player)
                .name(nickname)
                .race(race)
                .isMainCharacter(true)
                .build();

        characterRepository.save(character);
    }

    public List<Character> getAllCharacters(Player player) {
        return characterRepository.getCharactersByPlayer(player);
    }

    public Character getMainCharacter(Player player) {
        return characterRepository.getMainCharacter(player);
    }

    public int getCountCharacters(Player player) {
        return characterRepository.countCharactersByPlayer(player);
    }

}
