package gulas.saveli.StadtLandFluss.security.config;
import gulas.saveli.StadtLandFluss.game.websocket.GameConnectionHandler;
import gulas.saveli.StadtLandFluss.game.websocket.GameTimerLogic;
import gulas.saveli.StadtLandFluss.security.auth.websocket.UsernameHandShakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketSecurityConfig implements WebSocketConfigurer {
    @Autowired
    private final GameTimerLogic countdownWebSocketHandler;
    @Autowired
    private final GameConnectionHandler gameConnectionWebSocketHandler;
    @Autowired
    private final UsernameHandShakeInterceptor usernameHandShakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(countdownWebSocketHandler, "/timer/{gameId}")
                .addInterceptors(usernameHandShakeInterceptor)
                .setAllowedOrigins("*")
                .withSockJS();
        registry.addHandler(gameConnectionWebSocketHandler, "ws/{gameId}")
                .addInterceptors(usernameHandShakeInterceptor)
                .setAllowedOrigins("*");
    }
}