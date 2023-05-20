package gulas.saveli.StadtLandFluss.game.websocket.models;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@Builder
@RequiredArgsConstructor
public class WebSocketSessionPlayerSave {
    private WebSocketSession session;
    private String username;
}
