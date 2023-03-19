package gulas.saveli.StadtLandFluss.game.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RandomUtils {
    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Random random = new Random();

    public List<Character> getRandomCharacters(int size) {
         List<Character> characters = new ArrayList<>();
         for(int i = 0; i < size; i++) {
             characters.add(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
         }
         return characters;
    }
}
