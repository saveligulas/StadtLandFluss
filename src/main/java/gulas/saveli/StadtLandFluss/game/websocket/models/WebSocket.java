package gulas.saveli.StadtLandFluss.game.websocket.models;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.game.websocket.models.WebSocketSessionGameSave;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

public class WebSocket {
    public final Map<Long, List<WebSocketSession>> webSocketSessionsMap = new HashMap<>(); //TODO implement new models of saves
    private final Map<Long, List<String>> gameUsernameMap = new HashMap<>();

    public void sendMessageToAll(String message, Long id) {
        try {
            for(WebSocketSession session : webSocketSessionsMap.get(id)) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, WebSocketSession session) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Long getGameIdFromSession(WebSocketSession session) {
        String url = Objects.requireNonNull(session.getUri()).toString();
        String gameIdStr = url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'));
        return Long.parseLong(gameIdStr);
    }

    public void addSessionToMaps(WebSocketSession session, Long gameId, String username) {
        if(webSocketSessionsMap.containsKey(gameId)) {
            webSocketSessionsMap.get(gameId).add(session);
        } else {
            webSocketSessionsMap.put(gameId, new ArrayList<>(List.of(session)));
        }
        if(gameUsernameMap.containsKey(gameId)) {
            if(gameUsernameMap.get(gameId).contains(username)) {
                throw new ApiRequestException("User with name already in session");
            }
            gameUsernameMap.get(gameId).add(username);
        } else {
            gameUsernameMap.put(gameId, new ArrayList<>(List.of(username)));
        }
    }

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public void removeSessionFromMaps(WebSocketSession session) {
        Long gameId = getGameIdFromSession(session);
        String username = getUsernameFromSession(session);
        for(int i = 0; i < webSocketSessionsMap.get(gameId).size(); i++) {
            if(webSocketSessionsMap.get(gameId).get(i).getId().equals(session.getId())) {
                webSocketSessionsMap.get(gameId).remove(i);
            }
        }
        for(int i = 0; i < gameUsernameMap.get(gameId).size(); i++) {
            if(gameUsernameMap.get(gameId).get(i).equals(username)) {
                gameUsernameMap.get(gameId).remove(i);
            }
        }
    }

    private boolean checkHeaders(WebSocketSession session) {
        return session.getAttributes().get("Username") != null;
    }

    public boolean checkUsernameIsValid(WebSocketSession session, List<String> gamePlayers) {
        if(checkHeaders(session)) {
            String username = session.getAttributes().get("Username").toString();
            return gamePlayers.contains(username);
        }
        return false;
    }

    public boolean checkUsernameIsHost(WebSocketSession session, String hostUsername) {
        String username = session.getAttributes().get("Username").toString();
        return username.equals(hostUsername);
    }

    public String getUsernameFromSession(WebSocketSession session) {
        return session.getAttributes().get("Username").toString();
    }
}
