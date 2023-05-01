package gulas.saveli.StadtLandFluss.game.websocket;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.models.GameTimer;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.*;

@Component
public class GameTimerLogic extends WebSocket implements WebSocketHandler {

    private static final int COUNTDOWN_SECONDS = 5 * 60;
    private final Map<Long, GameTimer> gameIdGameTimerMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long gameId = getGameIdFromSession(session);
        if(!webSocketSessionsMap.containsKey(gameId)) {
            webSocketSessionsMap.put(gameId, new ArrayList<>(List.of(session)));
            gameIdGameTimerMap.put(gameId,
                    GameTimer.builder()
                            .gameId(gameId)
                            .isRunning(false)
                            .timer(new Timer())
                            .build());
        } else {
            webSocketSessionsMap.get(gameId).add(session);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        Long gameId = getGameIdFromSession(session);
        if(payload.isEmpty() || payload.startsWith(" ") || payload.length() > 10) {
            throw new ApiRequestException("Invalid payload");
        }
        if(payload.equals("START")) {
            startTimer(gameId);
        }
        if(payload.equals("END")) {
            stopTimer(gameId);
        }
        if(payload.equals("GAME_OVER")) {
            gameIdGameTimerMap.remove(gameId);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        removeSessionFromMap(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        removeSessionFromMap(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void startTimer(Long id) {
        if(!gameIdGameTimerMap.get(id).getIsRunning()) {
            gameIdGameTimerMap.get(id).purgeTimer();
            gameIdGameTimerMap.get(id).setIsRunning(true);
            gameIdGameTimerMap.get(id).getTimer().schedule(new TimerTask() {
                int countdown = COUNTDOWN_SECONDS;
                @Override
                public void run() {
                    countdown--;
                    if (countdown >= 0) {
                        sendMessageToAll(Integer.toString(countdown), id);
                    } else {
                        stopTimer(id);
                    }
                }
            }, 0, 1000);
        }
    }

    private void stopTimer(Long id) {
        if (gameIdGameTimerMap.get(id) != null) {
            gameIdGameTimerMap.get(id).purgeTimer();
            gameIdGameTimerMap.get(id).setIsRunning(false);
        }
    }
}
