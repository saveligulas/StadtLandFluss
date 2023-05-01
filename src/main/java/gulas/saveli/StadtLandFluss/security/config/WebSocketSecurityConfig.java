package gulas.saveli.StadtLandFluss.security.config;
import gulas.saveli.StadtLandFluss.game.websocket.GameConnectionHandler;
import gulas.saveli.StadtLandFluss.game.websocket.GameTimerLogic;
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

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(countdownWebSocketHandler, "/ws/timer/{gameId}")
                .setAllowedOrigins("*")
                .addHandler(gameConnectionWebSocketHandler, "/ws/{gameId}")
                .setAllowedOrigins("*");
    }

    private class MyHandshakeInterceptor implements HandshakeInterceptor {
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            List<String> username = request.getHeaders().get("Username");
            if(!username.isEmpty()) {
                attributes.put("Username", username.get(0));
            }
            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        }
    }
}