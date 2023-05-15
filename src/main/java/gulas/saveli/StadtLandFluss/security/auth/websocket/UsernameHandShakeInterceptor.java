package gulas.saveli.StadtLandFluss.security.auth.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Component
public class UsernameHandShakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        List<String> protocols = request.getHeaders().get("Sec-WebSocket-Protocol");
        System.out.println(request.getHeaders().toString());
        if (protocols != null && !protocols.isEmpty()) {
            String protocol = protocols.get(0);
            String username = extractUsernameFromProtocol(protocol);
            System.out.println(username);
            attributes.put("Username", username);
        }
        return true;
    }

    private String extractTokenFromPayload(InputStream body) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(body));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Authorization:")) {
                return line.substring(line.indexOf(":") + 1).trim();
            }
        }
        return null;
    }

    private String extractUsernameFromProtocol(String protocol) {
        // Extract the username from the protocol name
        String[] parts = protocol.split("-");
        return parts[parts.length - 1];
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println(response.toString());
    }
}
