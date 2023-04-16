package gulas.saveli.StadtLandFluss.game.logic.timer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class GameTimer extends TextWebSocketHandler {

    private final Map<String, Timer> timers = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String gameId = session.getUri().getPath().split("/")[2];
        System.out.println("New connection for gameId: " + gameId);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int timeLeft = 5 * 60; // 5 minutes

            @Override
            public void run() {
                timeLeft--;
                if (timeLeft == 0) {
                    timer.cancel();
                    timer.purge();
                    try {
                        session.close(CloseStatus.NORMAL.withReason("Game over!"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    int minutes = timeLeft / 60;
                    int seconds = timeLeft % 60;
                    String timerValue = String.format("%02d:%02d", minutes, seconds);
                    try {
                        session.sendMessage(new TextMessage(timerValue));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
        timers.put(gameId, timer);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String gameId = session.getUri().getPath().split("/")[2];
        System.out.println("Connection closed for gameId: " + gameId);
        Timer timer = timers.remove(gameId);
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

}
