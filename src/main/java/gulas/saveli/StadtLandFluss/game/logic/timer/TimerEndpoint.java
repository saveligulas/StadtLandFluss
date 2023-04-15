package gulas.saveli.StadtLandFluss.game.logic.timer;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@ServerEndpoint("/timer/{gameId}")
public class TimerEndpoint {

    private static final Map<String, List<WebSocketSession>> sessionsMap = new HashMap<>();
    private static final Map<String, Timer> timersMap = new HashMap<>();

    @OnOpen
    public void onOpen(WebSocketSession session, @PathParam("gameId") String gameId) throws IOException {
        List<WebSocketSession> gameSessions = sessionsMap.getOrDefault(gameId, new ArrayList<>());
        gameSessions.add(session);
        sessionsMap.put(gameId, gameSessions);
        session.getAttributes().put("gameId", gameId);
    }

    @OnClose
    public void onClose(WebSocketSession session) throws IOException {
        String gameId = (String) session.getAttributes().get("gameId");
        List<WebSocketSession> gameSessions = sessionsMap.get(gameId);
        gameSessions.remove(session);
        session.getAttributes().remove("gameId");

        if (gameSessions.isEmpty()) {
            Timer timer = timersMap.get(gameId);
            if (timer != null) {
                timer.cancel();
                timer.purge();
                timersMap.remove(gameId);
            }
        }
    }

    @OnMessage
    public void onMessage(String message, WebSocketSession session) throws IOException {
        if (message.equals("join")) {
            String gameId = (String) session.getAttributes().get("gameId");
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
