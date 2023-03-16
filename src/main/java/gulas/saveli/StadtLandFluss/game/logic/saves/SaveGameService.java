package gulas.saveli.StadtLandFluss.game.logic.saves;

import gulas.saveli.StadtLandFluss.repo.SaveGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SaveGameService {

    @Autowired
    private final SaveGameRepository saveGameRepository;

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
