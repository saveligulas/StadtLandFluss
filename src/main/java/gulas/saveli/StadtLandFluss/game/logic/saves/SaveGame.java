package gulas.saveli.StadtLandFluss.game.logic.saves;

import gulas.saveli.StadtLandFluss.game.logic.GameData;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveGame extends GameData {
    private Map<String, List<String>> userInputs;
}
