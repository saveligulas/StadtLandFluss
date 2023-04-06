package gulas.saveli.StadtLandFluss.game.logic.timer;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;

@ServerEndpoint("/timer/{gameId}")
public class TimerEndpoint {

    private static final Map<String, List<Session>> sessionsMap = new HashMap<>();
    private static final Map<String, Timer> timersMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") String gameId) {
        List<Session> gameSessions = sessionsMap.getOrDefault(gameId, new ArrayList<>());
        gameSessions.add(session);
        sessionsMap.put(gameId, gameSessions);
        session.getUserProperties().put("gameId", gameId);
    }

    @OnClose
    public void onClose(Session session) {
        String gameId = (String) session.getUserProperties().get("gameId");
        List<Session> gameSessions = sessionsMap.get(gameId);
        gameSessions.remove(session);
        session.getUserProperties().remove("gameId");

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
    public void onMessage(String message, Session session) {
        if (message.equals("join")) {
            String gameId = (String) session.getUserProperties().get("gameId");
            List<Session> gameSessions = sessionsMap.get(gameId);
            gameSessions.add(session);
            session.getUserProperties().put("playerIndex", gameSessions.size() - 1);

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
        List<Session> gameSessions = sessionsMap.get(gameId);
        for (Session session : gameSessions) {
            try {
                session.getBasicRemote().sendText(timerValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
