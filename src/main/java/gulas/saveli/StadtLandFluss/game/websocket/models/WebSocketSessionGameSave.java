package gulas.saveli.StadtLandFluss.game.websocket.models;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Timer;

@Data
@Builder
@RequiredArgsConstructor
public class WebSocketSessionGameSave {
    private Long id;
    private List<WebSocketSessionPlayerSave> playerSaves;
    private Timer timer;
    private GameState gameState;
}
