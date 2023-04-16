package gulas.saveli.StadtLandFluss.game.logic.timer;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
@RequestMapping("/timer/{gameId}")
public class TimerEndpoint extends TextWebSocketHandler implements SchedulingConfigurer {

    private final Map<String, List<WebSocketSession>> sessionsMap = new ConcurrentHashMap<>();
    private final Map<String, ScheduledFuture<?>> timerFuturesMap = new ConcurrentHashMap<>();



}
