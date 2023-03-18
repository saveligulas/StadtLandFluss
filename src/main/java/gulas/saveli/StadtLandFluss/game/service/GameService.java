package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.logic.model.Category;
import gulas.saveli.StadtLandFluss.game.logic.model.Game;
import gulas.saveli.StadtLandFluss.repo.CategoryRepository;
import gulas.saveli.StadtLandFluss.repo.GameRepository;
import gulas.saveli.StadtLandFluss.repo.UserRepository;
import gulas.saveli.StadtLandFluss.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.ClassUtils.isPresent;

@Service
@RequiredArgsConstructor
public class GameService {

    @Autowired
    private final GameRepository gameRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void hostGame(String username, Integer rounds, List<Character> characters, List<String> categoryNames) {
        List<Category> categoryList = new ArrayList<>();
        for(String str : categoryNames) {
            Optional<Category> categoryOptional = categoryRepository.findByName(str);
            if(categoryOptional.isEmpty()) {
                Category category = new Category(str, false);
                categoryRepository.save(category);
                categoryList.add(category);
            } else {
                Category category = categoryOptional.get();
                categoryList.add(category);
            }
        }
        Optional<User> optionalUser = gameRepository.findByUsername(username);
        if(optionalUser.isPresent()) {
            throw new ApiRequestException("User with username " + optionalUser.get().getEmail() + " has already hosted a game");
        }
        optionalUser = userRepository.findByEmail(username); //TODO
        Game game = new Game(username, rounds, characters, categoryList);
        game.setPlayers(new ArrayList<>(Arrays.asList()));
        gameRepository.save(game);

    }
}
