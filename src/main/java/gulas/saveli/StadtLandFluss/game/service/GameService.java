package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.logic.model.Category;
import gulas.saveli.StadtLandFluss.game.logic.model.Game;
import gulas.saveli.StadtLandFluss.game.logic.model.resp.GameResponse;
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

@Service
@RequiredArgsConstructor
public class GameService {

    @Autowired
    private final GameRepository gameRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RandomUtils randomUtils;

    public Game hostGame(String username) {
        List<Category> categoryList = new ArrayList<>();
        List<String> categoryNames = new ArrayList<>() {{
            add("Stadt");
            add("Land");
            add("Fluss");
        }};
        for(String str : categoryNames) {
            Optional<Category> categoryOptional = categoryRepository.findByName(str);
            Category category;
            if(categoryOptional.isEmpty()) {
                category = new Category(str, false);
                categoryRepository.save(category);
            } else {
                category = categoryOptional.get();
            }
            categoryList.add(category);
        }
        Optional<Game> optionalGame = gameRepository.findByHostUsername(username);
        if(optionalGame.isPresent()) {
            throw new ApiRequestException("User with email " + username + " has already hosted a game");
        }
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty()) {
            System.out.println("could not find user");
            throw new ApiRequestException("User with email " + username + " does not exist");
        }
        if(optionalUser.get().isPlaying()) {
            System.out.println("User in game");
            throw new ApiRequestException("User is already in a game");
        }
        Integer rounds = 3;
        List<Character> characters = randomUtils.getRandomCharacters(3);
        Game game = new Game(username, rounds, characters, categoryList);
        game.setPlayers(new ArrayList<>(Arrays.asList(optionalUser.get())));
        gameRepository.save(game);
        return game;
    }

    @Transactional
    public void hostGame(String username, Integer rounds, List<Character> characters, List<String> categoryNames) {
        List<Category> categoryList = new ArrayList<>();
        for(String str : categoryNames) {
            Optional<Category> categoryOptional = categoryRepository.findByName(str);
            Category category;
            if(categoryOptional.isEmpty()) {
                category = new Category(str, false);
                categoryRepository.save(category);
            } else {
                category = categoryOptional.get();
            }
            categoryList.add(category);
        }
        Optional<Game> optionalGame = gameRepository.findByHostUsername(username);
        if(optionalGame.isPresent()) {
            throw new ApiRequestException("User with username " + username + " has already hosted a game");
        }
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty()) {
            throw new ApiRequestException("User with email " + username + " does not exist");
        }
        if(optionalUser.get().getIsInGame()) {
            throw new ApiRequestException("User is already in a game");
        }
        Game game = new Game(username, rounds, characters, categoryList);
        game.setPlayers(new ArrayList<>(Arrays.asList(optionalUser.get())));
        gameRepository.save(game);
    }

    public List<GameResponse> getGamesResponseList() {
        List<Game> games = gameRepository.findAll();
        if(games.size() == 0) {
            return null;
        }
        List<GameResponse> gameResponses = new ArrayList<>();
        GameResponse response = new GameResponse();
        for(Game game : games) {
            response.setId(game.getId());
            response.setHostUsername(game.getHostUsername());
            response.setPlayerCount(game.getPlayerCount());
            gameResponses.add(response);
        }
        return gameResponses;
    }
}
