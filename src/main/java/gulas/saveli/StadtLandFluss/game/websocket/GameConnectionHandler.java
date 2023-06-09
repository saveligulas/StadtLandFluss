package gulas.saveli.StadtLandFluss.game.websocket;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.models.Game;
import gulas.saveli.StadtLandFluss.game.websocket.models.WebSocket;
import gulas.saveli.StadtLandFluss.repo.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@RequiredArgsConstructor
public class GameConnectionHandler extends WebSocket implements WebSocketHandler {

    @Autowired
    private final GameRepository gameRepository;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long gameId = getGameIdFromSession(session);
        String username = getUsernameFromSession(session);
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ApiRequestException("game with id " + gameId + " does not exist"));
        addSessionToMaps(session, gameId, username);
        if(game.getHasStarted() || game.getHasExpired() || game.isFull()) {
            throw new ApiRequestException("game has started, expired or is full");
        }
        sendMessageToAll(username+ " | " + gameId+ " | " + webSocketSessionsMap.toString(), gameId);
        sendMessageToAll(String.valueOf(webSocketSessionsMap.get(gameId).size()) + ": Session count", gameId);
    }

    @Override
    @Transactional
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Long gameId = getGameIdFromSession(session);
        System.out.println(gameId);
        String textMessage = message.getPayload().toString();
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ApiRequestException("game with id " + gameId + " does not exist"));
        if(textMessage.equals("START")) {
            game.setHasStarted(true);
            sendMessageToAll("GAME_STARTED", gameId);
        }
        if(textMessage.equals("END")) {
            game.setHasExpired(true);
            sendMessageToAll("GAME_OVER", gameId);
        }
        if(textMessage.equals("NEXT_ROUND")) {
            game.advanceRound();
            sendMessageToAll("GAME_ROUND_"+game.getCurrentRound(), gameId);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        removeSessionFromMaps(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        removeSessionFromMaps(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
