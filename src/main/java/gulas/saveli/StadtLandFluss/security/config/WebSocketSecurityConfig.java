package gulas.saveli.StadtLandFluss.security.config;
import gulas.saveli.StadtLandFluss.game.websocket.GameConnectionHandler;
import gulas.saveli.StadtLandFluss.game.websocket.GameTimerLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketSecurityConfig implements WebSocketConfigurer {
    @Autowired
    private final GameTimerLogic countdownWebSocketHandler;
    @Autowired
    private final GameConnectionHandler gameConnectionWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(countdownWebSocketHandler, "/ws/timer/{gameId}")
                .setAllowedOrigins("*")
                .addHandler(gameConnectionWebSocketHandler, "/ws/{gameId}")
                .setAllowedOrigins("*");
    }
}