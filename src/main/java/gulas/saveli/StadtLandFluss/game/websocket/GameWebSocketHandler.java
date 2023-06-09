package gulas.saveli.StadtLandFluss.game.websocket;

import gulas.saveli.StadtLandFluss.game.websocket.models.GameWebSocketViewModel;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GameWebSocketHandler extends GameWebSocketViewModel implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long gameId = getGameIdFromSession(session);
        String username = getUsernameFromSession(session);
        saveWebSocketSession(gameId, username, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Long gameId = getGameIdFromSession(session);
        String username = getUsernameFromSession(session);
        if(message.getPayload().toString().equals("NEXT_ROUND")) {

        }
        if(message.getPayload().toString().equals("END")) {

        }
        if(message.getPayload().toString().equals("CHANGES_APPLIED")) {

        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        deleteSession(session, getGameIdFromSession(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        deleteSession(session, getGameIdFromSession(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
