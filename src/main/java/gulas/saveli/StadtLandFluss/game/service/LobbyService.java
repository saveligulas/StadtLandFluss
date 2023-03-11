package gulas.saveli.StadtLandFluss.game.service;

import gulas.saveli.StadtLandFluss.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LobbyService {

    private final UserRepository userRepository;


}
