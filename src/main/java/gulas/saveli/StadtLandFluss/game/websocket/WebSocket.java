package gulas.saveli.StadtLandFluss.game.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WebSocket {
    public final Map<Long, List<WebSocketSession>> webSocketSessionsMap = new HashMap<>();

    public void sendMessage(String message, Long id) {
        try {
            for(WebSocketSession session : webSocketSessionsMap.get(id)) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Long getGameIdFromSession(WebSocketSession session) {
        String url = Objects.requireNonNull(session.getUri()).toString();
        String gameIdStr = url.substring(url.lastIndexOf('/') + 1);
        return Long.parseLong(gameIdStr);
    }

    public void removeSessionFromMap(WebSocketSession session) {
        Long gameId = getGameIdFromSession(session);
        for(int i = 0; i < webSocketSessionsMap.get(gameId).size(); i++) {
            if(webSocketSessionsMap.get(gameId).get(i).getId().equals(session.getId())) {
                webSocketSessionsMap.get(gameId).remove(i);
            }
        }
    }
}
