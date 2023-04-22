package cc.abro.telegramgamebot.services;

import cc.abro.telegramgamebot.db.entity.Character;
import cc.abro.telegramgamebot.db.entity.Player;
import cc.abro.telegramgamebot.db.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public void createMainCharacter(Player player, String nickname) {
        Character character = Character.builder()
                .player(player)
                .name(nickname)
                .isMainCharacter(true)
                .build();

        characterRepository.save(character);
    }

    public List<Character> getAllCharacters(Player player) {
        return characterRepository.getCharactersByPlayer(player);
    }

    public Character getMainCharacter(Player player) {
        return characterRepository.getCharacterByPlayerAndIsMainCharacterIsTrue(player);
    }

    public int getCountCharacters(Player player) {
        return characterRepository.countCharactersByPlayer(player);
    }

}
