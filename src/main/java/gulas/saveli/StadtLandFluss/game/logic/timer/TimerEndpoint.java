package gulas.saveli.StadtLandFluss.game.logic.timer;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
@RequestMapping("/timer/{gameId}")
public class TimerEndpoint extends TextWebSocketHandler{

    private final Map<String, List<WebSocketSession>> sessionsMap = new ConcurrentHashMap<>();
    private final Map<String, ScheduledFuture<?>> timerFuturesMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String gameId = getGameId(session);
        sessionsMap.computeIfAbsent(gameId, key -> new CopyOnWriteArrayList<>()).add(session);
        session.getAttributes().put("gameId", gameId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String gameId = getGameId(session);
        List<WebSocketSession> gameSessions = sessionsMap.get(gameId);
        if (gameSessions != null) {
            gameSessions.remove(session);
            if (gameSessions.isEmpty()) {
                ScheduledFuture<?> timerFuture = timerFuturesMap.get(gameId);
                if (timerFuture != null) {
                    timerFuture.cancel(true);
                    timerFuturesMap.remove(gameId);
                }
            }
        }
    }



    private String getGameId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private void sendTimerUpdate(String gameId, String timerValue) {
        List<WebSocketSession> gameSessions = sessionsMap.get(gameId);
        for (WebSocketSession session : gameSessions) {
            try {
                session.sendMessage(new TextMessage(timerValue));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
