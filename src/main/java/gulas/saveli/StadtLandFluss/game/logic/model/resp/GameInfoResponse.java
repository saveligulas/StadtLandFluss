package gulas.saveli.StadtLandFluss.game.logic.model.resp;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Data
@ResponseBody
@Builder
public class GameInfoResponse {

    private List<String> players = new ArrayList<>();
    private Integer rounds;
    private List<String> categoryNames = new ArrayList<>();

}
