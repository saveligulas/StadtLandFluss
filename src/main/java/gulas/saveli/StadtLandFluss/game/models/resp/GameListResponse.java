package gulas.saveli.StadtLandFluss.game.models.resp;

import lombok.Data;

@Data
public class GameListResponse {

    private Long id;
    private String hostUsername;
    private Integer playerCount;
    private Integer maxPlayers;

}
