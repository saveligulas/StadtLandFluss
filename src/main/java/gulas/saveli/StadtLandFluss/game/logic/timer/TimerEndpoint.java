package gulas.saveli.StadtLandFluss.game.logic.timer;

import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/timer/{gameId}")
public class TimerEndpoint {

    private static final Map<String, Timer> gameTimers = new ConcurrentHashMap<>();


}
