package gulas.saveli.StadtLandFluss.game.logic.timer;

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


        startTimer();
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        stopTimer();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        stopTimer();
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
                    sendCountdownMessage(countdown);
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

    private void sendCountdownMessage(int countdown) {
        try {
            session.sendMessage(new TextMessage(Integer.toString(countdown)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
