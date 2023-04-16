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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;

@Component
public class TimerEndpoint extends TextWebSocketHandler{

    private static final Map<String, List<WebSocketSession>> sessionsMap = new HashMap<>();
    private static final Map<String, Timer> timersMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String gameId = getGameId(session);
        List<WebSocketSession> gameSessions = sessionsMap.getOrDefault(gameId, new ArrayList<>());
        gameSessions.add(session);
        sessionsMap.put(gameId, gameSessions);
        session.getAttributes().put("gameId", gameId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (message.getPayload().equals("join")) {
            String gameId = getGameId(session);
            List<WebSocketSession> gameSessions = sessionsMap.get(gameId);
            gameSessions.add(session);
            session.getAttributes().put("playerIndex", gameSessions.size() - 1);

            if (gameSessions.size() == 1) {
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    int timeLeft = 10 * 60; // 10 minutes

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
                timersMap.put(gameId, timer);
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
