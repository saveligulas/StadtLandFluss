package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.models.AnswerList;
import gulas.saveli.StadtLandFluss.game.models.Category;
import gulas.saveli.StadtLandFluss.game.models.Game;
import gulas.saveli.StadtLandFluss.game.models.req.GameSettingRequest;
import gulas.saveli.StadtLandFluss.game.models.resp.GameInfoResponse;
import gulas.saveli.StadtLandFluss.game.models.resp.GameListResponse;
import gulas.saveli.StadtLandFluss.game.models.resp.GameSettingResponse;
import gulas.saveli.StadtLandFluss.repo.CategoryRepository;
import gulas.saveli.StadtLandFluss.repo.GameRepository;
import gulas.saveli.StadtLandFluss.repo.UserRepository;
import gulas.saveli.StadtLandFluss.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Autowired
    private final AnswerListService answerListService;
    @Autowired
    private final CategoryService categoryService;

    public Long hostGame(String username) {
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
        game.setMaxPlayers(10);
        gameRepository.save(game);
        Game createdGame = gameRepository.findByHostUsername(username)
                .orElseThrow(() -> new ApiRequestException("Error creating and loading game"));
        return createdGame.getId();
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

    @Transactional
    public void setupGame(Long gameId) {
        Game game = getGame(gameId);

        List<String> playerUsernames = new ArrayList<>();
        for(User user : game.getPlayers()) {
            playerUsernames.add(user.getEmail());
        }
        List<AnswerList> answerLists = answerListService.setupEmptyAnswerLists(playerUsernames.size(), game.getCategories());
        HashMap<String, AnswerList> usernameAnswerListMap = new HashMap<>();
        for(int i = 0; i < answerLists.size(); i++) {
            usernameAnswerListMap.put(playerUsernames.get(i), answerLists.get(i));
        }
        game.setUsernameAnswerMap(usernameAnswerListMap);
    }

    public List<GameListResponse> getGamesResponseList() {
        List<Game> games = gameRepository.findAll();
        if(games.size() == 0) {
            return null;
        }
        List<GameListResponse> gameResponses = new ArrayList<>();
        GameListResponse response = new GameListResponse();
        for(Game game : games) {
            response.setId(game.getId());
            response.setHostUsername(game.getHostUsername());
            response.setPlayerCount(game.getPlayerCount());
            response.setMaxPlayers(game.getMaxPlayers());
            gameResponses.add(response);
        }
        return gameResponses;
    }

    public Boolean isGameHost(Long gameId, String host) {
        Game game = getGame(gameId);
        return game.getHostUsername().equals(host);
    }

    @Transactional
    public GameSettingResponse changeGameSettings(Long gameId, GameSettingRequest settingRequest) {
        Game game = getGame(gameId);

        GameSettingResponse gameSettingResponse = new GameSettingResponse();
        List<String> categoryNameList = settingRequest.getCategoryNames();
        if(!categoryNameList.isEmpty() && categoryNameList.size() >= 3) {
            List<Category> categories = new ArrayList<>();
            for (String categoryName : categoryNameList) {
                if (categoryName.isEmpty()) {
                    throw new ApiRequestException("Invalid category name: " + categoryName);
                }
                categories.add(categoryService.convertCategoryString(categoryName));
            }
            game.setCategories(categories);
            gameSettingResponse.setCategoryMessage("Categories successfully changed");
        }

        Integer rounds = settingRequest.getRounds();
        if(rounds != null && rounds > 0 && !rounds.equals(game.getRounds())) {
            game.setRounds(rounds);
            gameSettingResponse.setRoundsMessage("Rounds successfully changed");
        }

        List<Character> characters = settingRequest.getCharacters();
        if(characters != null && characters.size() > 0 && !characters.equals(game.getCharacters())) {
            game.setCharacters(characters);
            gameSettingResponse.setCharacterMessage("Characters successfully set");
        }

        return gameSettingResponse;
    }

    public GameInfoResponse buildGameInfo(Long gameId) {
        Game game = getGame(gameId);

        List<String> playerNames = new ArrayList<>();
        for(User user : game.getPlayers()) {
            playerNames.add(user.getEmail());
        }
        List<String> categoryNames = new ArrayList<>();
        for(Category category : game.getCategories()) {
            categoryNames.add(category.getName());
        }
        return GameInfoResponse.builder()
                .players(playerNames)
                .categoryNames(categoryNames)
                .rounds(game.getRounds())
                .id(game.getId())
                .build();
    }

    @Transactional
    public Boolean joinGame(Long gameId, String username) {
        Game game = getGame(gameId);

        if(game.isFull() || game.isPlayerInGame(username)) {
            throw new ApiRequestException("error processing join request");
        }

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ApiRequestException("user does not exist"));

        game.addPlayer(user);
        return true;
    }

    @Transactional
    public void disconnectUser(Long gameId, String username) {
        Game game = getGame(gameId);

        if(!game.isPlayerInGame(username) || !game.isPlayerInGame(username)) {
            throw new ApiRequestException("error processing disconnect");
        }

        User user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new ApiRequestException("user does not exist"));

        game.removePlayer(user);
    }

    public Game getGame(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new ApiRequestException("game with id " + gameId + " does not exist"));
    }
}
