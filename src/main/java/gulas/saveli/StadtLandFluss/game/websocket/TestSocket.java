package gulas.saveli.StadtLandFluss.game.websocket;

import gulas.saveli.StadtLandFluss.game.websocket.models.GameWebSocketViewModel;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class TestSocket extends GameWebSocketViewModel implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(getGameIdFromSession(session) + " - " + getUsernameFromSession(session));
        saveWebSocketSession(getGameIdFromSession(session), getUsernameFromSession(session), session);
        sendMessageToAllPlayersOfAllGames("All Games Check");
        sendMessageToAllPlayersOfGame(getGameIdFromSession(session), "Specific Game Check");
        sendMessageToAllPlayersOfGame(getGameIdFromSession(session), "Player count : " + getPlayerCountOfGame(getGameIdFromSession(session)));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        deleteSession(session, getGameIdFromSession(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
