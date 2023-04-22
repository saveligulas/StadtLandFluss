package gulas.saveli.StadtLandFluss.game.logic.timer;

import org.apache.tomcat.websocket.server.UriTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;

@Component
public class GameTimer implements WebSocketHandler {

    private static final int COUNTDOWN_SECONDS = 5 * 60;
    private Timer timer;
    private final Map<Long, Timer> gameIdTimerMap = new HashMap<>();
    private final Map<Long, WebSocketSession> webSocketSessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long gameId = getGameIdFromSession(session);

        webSocketSessionMap.put(gameId, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
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

    private void stopTimer(Long id) {
        if (gameIdTimerMap.get(id) != null) {
            timer.cancel();
            gameIdTimerMap.put(id, null);
        }
    }

    private void sendCountdownMessage(int countdown, Long id) {
        try {
            webSocketSessionMap.get(id).sendMessage(new TextMessage(Integer.toString(countdown)));
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
        webSocketSessionMap.remove(getGameIdFromSession(session));
    }

}
