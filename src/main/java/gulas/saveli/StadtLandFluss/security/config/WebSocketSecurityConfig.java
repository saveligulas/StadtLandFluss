package gulas.saveli.StadtLandFluss.security.config;
import gulas.saveli.StadtLandFluss.game.logic.timer.TimerEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class WebSocketSecurityConfig  implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler((WebSocketHandler) timerEndpoint(), "/timer/{gameId}")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Bean
    public TimerEndpoint timerEndpoint() {
        return new TimerEndpoint();
    }
}