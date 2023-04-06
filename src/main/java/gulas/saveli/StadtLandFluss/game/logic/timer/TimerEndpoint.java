package gulas.saveli.StadtLandFluss.game.logic.timer;

import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/timer/{gameId}")
public class TimerEndpoint {

    private static final Map<String, Timer> gameTimers = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") String gameId) {
        gameTimers.computeIfAbsent(gameId, k -> {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    for (Session session : session.getOpenSessions()) {
                        if (session.getUserPrincipal() != null && gameId.equals(session.getUserPrincipal().getName())) {
                            session.getAsyncRemote().sendText("timer_event");
                        }
                    }
                }
            }, 0, 1000);
            return timer;
        });
    }
}
