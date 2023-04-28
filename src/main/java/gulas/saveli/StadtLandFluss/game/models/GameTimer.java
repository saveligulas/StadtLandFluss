package gulas.saveli.StadtLandFluss.game.models;

import lombok.Builder;
import lombok.Data;

import java.util.Timer;
import java.util.TimerTask;

@Data
@Builder
public class GameTimer {

    private Timer timer;
    private Boolean isRunning;
    private Long gameId;
    private int roundCount;

    public void purgeTimer() {
        this.timer.cancel();
        this.timer = new Timer();
        this.roundCount += 1;
    }
}
