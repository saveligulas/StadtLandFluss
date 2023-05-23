package gulas.saveli.StadtLandFluss.game.websocket;

import gulas.saveli.StadtLandFluss.game.websocket.models.WebSocketSessionGameSave;
import gulas.saveli.StadtLandFluss.game.websocket.models.WebSocketSessionPlayerSave;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public class GameWebSocketViewModel {
    private final List<WebSocketSessionGameSave> webSocketSessionGameSaves = new ArrayList<>();

    public void saveWebSocketSession(Long id, String username, WebSocketSession webSocketSession) {
        WebSocketSessionPlayerSave playerSave = WebSocketSessionPlayerSave.builder()
                .session(webSocketSession)
                .username(username)
                .build();
    }
}
