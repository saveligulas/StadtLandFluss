package gulas.saveli.StadtLandFluss.game.websocket;

import gulas.saveli.StadtLandFluss.repo.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Component
@RequiredArgsConstructor
public class GameConnectionHandler extends WebSocket implements WebSocketHandler {

    @Autowired
    private final GameRepository gameRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long gameId = getGameIdFromSession(session);
        if(!webSocketSessionsMap.containsKey(gameId)) {
            webSocketSessionsMap.put(gameId, new ArrayList<>(List.of(session)));
        } else {
            webSocketSessionsMap.get(gameId).add(session);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}