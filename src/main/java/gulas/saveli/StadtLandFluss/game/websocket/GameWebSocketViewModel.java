package gulas.saveli.StadtLandFluss.game.websocket;

import gulas.saveli.StadtLandFluss.game.websocket.models.GameState;
import gulas.saveli.StadtLandFluss.game.websocket.models.WebSocketSessionGameSave;
import gulas.saveli.StadtLandFluss.game.websocket.models.WebSocketSessionPlayerSave;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameWebSocketViewModel {
    private final List<WebSocketSessionGameSave> webSocketSessionGameSaves = new ArrayList<>();

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
            }
        }
        if(!foundGameSave) {
            webSocketSessionGameSaves.add(WebSocketSessionGameSave.builder()
                            .gameState(GameState.PRE)
                            .id(id)
                            .playerSaves(List.of(playerSave))
                    .build());
        }
    }
}
