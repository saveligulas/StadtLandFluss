package gulas.saveli.StadtLandFluss.game.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class TestSocket implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connected");
        session.sendMessage(new TextMessage(session.getAttributes().get("Username").toString()));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
