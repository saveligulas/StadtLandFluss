package gulas.saveli.StadtLandFluss.game.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebSocket {
    private final Map<Long, List<WebSocketSession>> webSocketSessionsMap = new HashMap<>();

    private void sendMessage(int countdown, Long id) {
        try {
            for(WebSocketSession session : webSocketSessionMap.get(id)) {
                session.sendMessage(new TextMessage(Integer.toString(countdown)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
