package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.game.models.resp.GameViewResponse;
import gulas.saveli.StadtLandFluss.repo.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InGameService {

    @Autowired
    private final GameRepository gameRepository;

    public GameViewResponse getGameViewResponse(long parseLong) {
        return null;
    }
}
