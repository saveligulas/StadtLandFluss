package gulas.saveli.StadtLandFluss.game.logic.timer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class GameTimer implements WebSocketHandler {

    private final Map<String, ConcurrentLinkedQueue<WebSocketSession>> gameSessionsMap = new ConcurrentHashMap<>();
    private final Map<String, Timer> gameTimersMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String gameId = getGameIdFromUri(session.getUri().getPath());

        ConcurrentLinkedQueue<WebSocketSession> gameSessions = gameSessionsMap.computeIfAbsent(gameId, k -> new ConcurrentLinkedQueue<>());
        gameSessions.add(new ConcurrentWebSocketSessionDecorator(session, 60 * 60 * 24 * 365)); // Set maxIdleTimeout to 1 year

        if (gameSessions.size() == 1) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int timeLeft = 5 * 60; // 5 minutes

                @Override
                public void run() {
                    timeLeft--;
                    if (timeLeft == 0) {
                        timer.cancel();
                        timer.purge();
                        sendTimerUpdate(gameId, "00:00");
                    } else {
                        int minutes = timeLeft / 60;
                        int seconds = timeLeft % 60;
                        String timerValue = String.format("%02d:%02d", minutes, seconds);
                        sendTimerUpdate(gameId, timerValue);
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, 1000);
            gameTimersMap.put(gameId, timer);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String gameId = getGameIdFromUri(session.getUri().getPath());

        ConcurrentLinkedQueue<WebSocketSession> gameSessions = gameSessionsMap.get(gameId);
        if (gameSessions != null) {
            gameSessions.remove(session);

            if (gameSessions.isEmpty()) {
                Timer timer = gameTimersMap.get(gameId);
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                    gameTimersMap.remove(gameId);
                }
            }
        }
    }

    private void sendTimerUpdate(String gameId, String timerValue) {
        ConcurrentLinkedQueue<WebSocketSession> gameSessions = gameSessionsMap.get(gameId);
        if (gameSessions != null) {
            for (WebSocketSession session : gameSessions) {
                try {
                    session.sendMessage(new TextMessage(timerValue));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getGameIdFromUri(String uri) {
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

}
