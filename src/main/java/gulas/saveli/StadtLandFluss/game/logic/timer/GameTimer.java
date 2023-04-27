package gulas.saveli.StadtLandFluss.game.logic.timer;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import org.apache.tomcat.websocket.server.UriTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;

@Component
public class GameTimer implements WebSocketHandler {

    private static final int COUNTDOWN_SECONDS = 5 * 60;
    private final Map<Long, Timer> gameIdTimerMap = new HashMap<>(); //TODO rework map and add custom model to save all information
    private final Map<Long, List<WebSocketSession>> webSocketSessionMap = new HashMap<>();
    private final Map<Long, Boolean> gameIdTimerIsRunningMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long gameId = getGameIdFromSession(session);
        if(!webSocketSessionMap.containsKey(gameId)) {
            webSocketSessionMap.put(gameId, new ArrayList<>(List.of(session)));
            gameIdTimerIsRunningMap.put(gameId, false);
        } else {
            webSocketSessionMap.get(gameId).add(session);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        Long gameId = getGameIdFromSession(session);
        if(payload.isEmpty() || payload.startsWith(" ") || payload.length() > 10) {
            throw new ApiRequestException("Invalid payload");
        }
        if(payload.equals("START")) {
            startTimer(gameId);
            gameIdTimerIsRunningMap.put(gameId, true);
        }
        if(payload.equals("END")) {
            stopTimer(gameId);
            gameIdTimerIsRunningMap.put(gameId, false);
        }
        if(payload.equals("GAME_OVER")) {
            gameIdTimerMap.remove(gameId);
            gameIdTimerIsRunningMap.remove(gameId);
        }
        if(payload.equals("NEXT_ROUND")) {

        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        removeSessionFromMap(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        removeSessionFromMap(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void startTimer(Long id) {
        if(!gameIdTimerIsRunningMap.get(id)) {
            gameIdTimerMap.put(id, new Timer());
            gameIdTimerMap.get(id).schedule(new TimerTask() {
                int countdown = COUNTDOWN_SECONDS;
                @Override
                public void run() {
                    countdown--;
                    if (countdown >= 0) {
                        sendCountdownMessage(countdown, id);
                    } else {
                        stopTimer(id);
                    }
                }
            }, 0, 1000);
        }
    }

    private void stopTimer(Long id) {
        if (gameIdTimerMap.get(id) != null) {
            gameIdTimerMap.put(id, null);
        }
    }

    private void sendCountdownMessage(int countdown, Long id) {
        try {
            for(WebSocketSession session : webSocketSessionMap.get(id)) {
                session.sendMessage(new TextMessage(Integer.toString(countdown)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long getGameIdFromSession(WebSocketSession session) {
        String url = Objects.requireNonNull(session.getUri()).toString();
        String gameIdStr = url.substring(url.lastIndexOf('/') + 1);
        return Long.parseLong(gameIdStr);
    }

    private void removeSessionFromMap(WebSocketSession session) {
        Long gameId = getGameIdFromSession(session);
        for(int i = 0; i < webSocketSessionMap.get(gameId).size(); i++) {
            if(webSocketSessionMap.get(gameId).get(i).getId().equals(session.getId())) {
                webSocketSessionMap.get(gameId).remove(i);
            }
        }

    }

}
