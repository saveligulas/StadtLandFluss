package gulas.saveli.StadtLandFluss.security.config;
import gulas.saveli.StadtLandFluss.game.logic.timer.GameTimer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketSecurityConfig implements WebSocketConfigurer {
    @Autowired
    private final GameTimer countdownWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(countdownWebSocketHandler, "/timer")
                .setAllowedOrigins("*");
    }
}