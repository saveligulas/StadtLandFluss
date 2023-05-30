package gulas.saveli.StadtLandFluss.game.websocket.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

@Data
@Builder
public class WebSocketSessionGameSave {
    private Long id;
    private List<WebSocketSessionPlayerSave> playerSaves = new ArrayList<>();
    private Timer timer = new Timer();
    private GameState gameState;

    public void addPlayerSave(WebSocketSessionPlayerSave playerSave) {
        this.playerSaves.add(playerSave);
    }

    public void removePlayerSave(int index) {
        this.playerSaves.remove(index);
    }
}
