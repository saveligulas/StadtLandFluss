package gulas.saveli.StadtLandFluss.security.auth.websocket;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class UsernameHandShakeInterceptor implements HandshakeInterceptor {
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        HttpHeaders headers = request.getHeaders();
//        List<String> protocols = headers.get("Sec-WebSocket-Protocol");
//        if (protocols != null && !protocols.isEmpty()) {
//            String protocol = protocols.get(0);
//            System.out.println(protocol);
//            attributes.put("Username", protocol);
//        } else {
//            throw new ApiRequestException("Invalid protocol used");
//        }
//        return true;
//    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        URI uri = request.getURI();
        String query = uri.getQuery();
        String username = extractUsernameFromQuery(query);
        System.out.println(username);
        if(username.isEmpty()) {
            throw new ApiRequestException("Invalid protocol");
        }
        attributes.put("Username", username);
        return true;
    }

    private String extractUsernameFromQuery(String query) {
        String[] queryParams = query.split("&");
        for (String param : queryParams) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && "username".equals(keyValue[0])) {
                return URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println(response.toString());
    }
}
