package gulas.saveli.StadtLandFluss.security.config;
import gulas.saveli.StadtLandFluss.game.logic.timer.TimerEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class WebSocketSecurityConfig  implements WebSocketConfigurer, WebSecurityConfigurer<WebSecurity> {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler((WebSocketHandler) timerEndpoint(), "/timer/{gameId}");
    }

    @Bean
    public TimerEndpoint timerEndpoint() {
        return new TimerEndpoint();
    }

    @Override
    public void init(WebSecurity web) throws Exception {
        web.addSecurityFilterChainBuilder()
    }

    @Override
    public void configure(WebSecurity builder) throws Exception {

    }
}