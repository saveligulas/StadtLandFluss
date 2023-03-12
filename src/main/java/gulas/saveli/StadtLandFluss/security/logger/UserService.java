package gulas.saveli.StadtLandFluss.security.logger;

import gulas.saveli.StadtLandFluss.repo.UserRepository;
import gulas.saveli.StadtLandFluss.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void setToken(long id, String token) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist"));
        user.setToken(token);
    }
}
