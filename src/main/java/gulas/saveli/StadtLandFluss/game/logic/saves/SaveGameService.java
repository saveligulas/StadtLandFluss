package gulas.saveli.StadtLandFluss.game.logic.saves;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaveGameService {

    private Map<String, List<String>> userInputs;

    public void clearService() {
        userInputs = null;
    }

    public void setUsers(List<String> usernames) {
        userInputs = new HashMap<>();
        for(String username : usernames) {
            userInputs.put(username, null);
        }
    }


}
