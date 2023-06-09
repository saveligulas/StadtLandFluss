package gulas.saveli.StadtLandFluss.security.config;
import gulas.saveli.StadtLandFluss.game.websocket.GameTimerLogic;
import gulas.saveli.StadtLandFluss.game.websocket.GameWebSocketHandler;
import gulas.saveli.StadtLandFluss.security.auth.websocket.UsernameHandShakeInterceptor;
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
    private final UsernameHandShakeInterceptor usernameHandShakeInterceptor;
    @Autowired
    private final GameWebSocketHandler gameWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(countdownWebSocketHandler, "/timer/{gameId}")
                .addInterceptors(usernameHandShakeInterceptor)
                .setAllowedOrigins("*")
                .withSockJS();
        registry.addHandler(gameWebSocketHandler, "/ws/{gameId}")
                .addInterceptors(usernameHandShakeInterceptor)
                .setAllowedOrigins("*");
    }
}