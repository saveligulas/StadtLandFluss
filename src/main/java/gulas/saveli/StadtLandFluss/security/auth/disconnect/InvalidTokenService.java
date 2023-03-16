package gulas.saveli.StadtLandFluss.security.auth.disconnect;

import gulas.saveli.StadtLandFluss.repo.InvalidTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvalidTokenService {

    @Autowired
    private final InvalidTokenRepository invalidTokenRepository;

    public void save(String token) {
        //TODO
    }
}
