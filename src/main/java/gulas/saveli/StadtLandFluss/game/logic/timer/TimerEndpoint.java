package gulas.saveli.StadtLandFluss.game.logic.timer;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/timer/{gameId}")
public class TimerEndpoint {

    private static final Map<String, List<Session>> sessionsMap = new HashMap<>();
    private static final Map<String, Timer> timersMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") String gameId) {
        // Add the session to the list of sessions for the game
        List<Session> gameSessions = sessionsMap.getOrDefault(gameId, new ArrayList<>());
        gameSessions.add(session);
        sessionsMap.put(gameId, gameSessions);
        session.getUserProperties().put("gameId", gameId);
    }

    @OnClose
    public void onClose(Session session) {
        // Remove the session from the list of sessions for the game
        String gameId = (String) session.getUserProperties().get("gameId");
        List<Session> gameSessions = sessionsMap.get(gameId);
        gameSessions.remove(session);
        session.getUserProperties().remove("gameId");

        // If there are no more sessions for the game, cancel the timer
        if (gameSessions.isEmpty()) {
            Timer timer = timersMap.get(gameId);
            if (timer != null) {
                timer.cancel();
                timer.purge();
                timersMap.remove(gameId);
            }
        }
    }
}
