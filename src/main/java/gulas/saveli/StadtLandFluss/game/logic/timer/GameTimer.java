package gulas.saveli.StadtLandFluss.game.logic.timer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;

@Component
public class GameTimer implements WebSocketHandler {

    private static final int COUNTDOWN_SECONDS = 5 * 60;

    private WebSocketSession session;
    private Timer timer;
    private int countdown;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        this.countdown = COUNTDOWN_SECONDS;
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

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                countdown--;
                if (countdown >= 0) {
                    sendCountdownMessage(countdown);
                } else {
                    stopTimer();
                }
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
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
