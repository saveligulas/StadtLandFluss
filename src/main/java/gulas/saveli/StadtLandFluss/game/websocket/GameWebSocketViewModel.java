package gulas.saveli.StadtLandFluss.game.websocket;

import gulas.saveli.StadtLandFluss.game.websocket.models.WebSocketSessionGameSave;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public class GameWebSocketViewModel {
    private final List<WebSocketSessionGameSave> webSocketSessionGameSaves = new ArrayList<>();

    public void saveWebSocketSession(Long id, WebSocketSession webSocketSession) {

    }
}
