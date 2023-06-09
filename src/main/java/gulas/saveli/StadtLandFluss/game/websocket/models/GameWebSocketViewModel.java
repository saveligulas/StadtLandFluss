package gulas.saveli.StadtLandFluss.game.websocket.models;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.websocket.models.GameState;
import gulas.saveli.StadtLandFluss.game.websocket.models.WebSocketSessionGameSave;
import gulas.saveli.StadtLandFluss.game.websocket.models.WebSocketSessionPlayerSave;
import lombok.SneakyThrows;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameWebSocketViewModel extends WebSocket {
    private List<WebSocketSessionGameSave> webSocketSessionGameSaves = new ArrayList<>();

    public void saveWebSocketSession(Long id, String username, WebSocketSession webSocketSession) {
        WebSocketSessionPlayerSave playerSave = WebSocketSessionPlayerSave.builder()
                .session(webSocketSession)
                .username(username)
                .build();
        boolean foundGameSave = false;
        for(int i = 0 ; i<webSocketSessionGameSaves.size() ; i++) {
            if(Objects.equals(webSocketSessionGameSaves.get(i).getId(), id)) {
                foundGameSave = true;
                webSocketSessionGameSaves.get(i).addPlayerSave(playerSave);
                break;
            }
        }
        if(!foundGameSave) {
            webSocketSessionGameSaves.add(WebSocketSessionGameSave.builder()
                            .gameState(GameState.PRE)
                            .id(id)
                            .playerSaves(new ArrayList<>(List.of(playerSave)))
                    .build());
        }
    }

    @SneakyThrows
    public void sendMessageToAllPlayersOfGame(Long id, String message) {
        for(WebSocketSessionPlayerSave playerSave : getGameSave(id).getPlayerSaves()) {
            playerSave.getSession().sendMessage(new TextMessage(message));
        }
    }

    @SneakyThrows
    public void sendMessageToAllPlayersOfAllGames(String message) {
        for(WebSocketSessionGameSave gameSave : webSocketSessionGameSaves) {
            for(WebSocketSessionPlayerSave playerSave : gameSave.getPlayerSaves()) {
                playerSave.getSession().sendMessage(new TextMessage(message));
            }
        }
    }

    public void deleteSession(WebSocketSession session, Long id) {
        for(int i = 0 ; i < webSocketSessionGameSaves.size(); i++) {
            if(webSocketSessionGameSaves.get(i).getId().equals(id)) {
                for(int j = 0 ; j < webSocketSessionGameSaves.get(i).getPlayerSaves().size(); j++) {
                    if(webSocketSessionGameSaves.get(i).getPlayerSaves().get(j).getSession().getId().equals(session.getId())) {
                        webSocketSessionGameSaves.get(i).removePlayerSave(j);
                    }
                }
            }
        }
    }

    public List<String> getConnectedUsernamesOfGame(Long id) {
        List<String> usernames = new ArrayList<>();
        for(WebSocketSessionPlayerSave playerSave : getGameSave(id).getPlayerSaves()) {
            usernames.add(playerSave.getUsername());
        }
        return  usernames;
    }

    public int getPlayerCountOfGame(Long id) {
        return getGameSave(id).getPlayerSaves().size();
    }

    private WebSocketSessionGameSave getGameSave(Long id) {
        for(WebSocketSessionGameSave gameSave : webSocketSessionGameSaves) {
            if(gameSave.getId().equals(id)) {
                return gameSave;
            }
        }
        throw new ApiRequestException("Invalid game id");
    }

    private int getIndexOfGameSave(Long id) {
        for(int i = 0; i<webSocketSessionGameSaves.size(); i++) {
            if(webSocketSessionGameSaves.get(i).getId().equals(id)) {
                return i;
            }
        }
        throw new ApiRequestException("Invalid game id");
    }
}
