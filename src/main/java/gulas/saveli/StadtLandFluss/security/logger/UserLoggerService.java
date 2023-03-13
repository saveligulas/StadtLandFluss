package gulas.saveli.StadtLandFluss.security.logger;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
import gulas.saveli.StadtLandFluss.repo.UserRepository;
import gulas.saveli.StadtLandFluss.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLoggerService {

    @Autowired
    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;

    public void connect(String token, String email) {
        List<String> tokens = userLogRepository.retrieveTokens();
        if(tokens.contains(token)) {
            throw new ApiRequestException("token already exists");
        }
        List<String> users = userLogRepository.retrieveUsers();
        if(users.contains(email)) {
            throw new ApiRequestException("user already exists");
        }
        userLogRepository.store(token, email);
    }

    public void disconnect(String token) {
        if(!userLogRepository.retrieveTokens().contains(token)) {
            throw new ApiRequestException("token not found");
        }
        userLogRepository.delete(token);
    }

    public List<String> getConnectedUsersNames() {
        List<String> emails = userLogRepository.retrieveUsers();
        List<String> names = new ArrayList<>();
        for(String email : emails) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiRequestException("user not found"));
            names.add(user.getFullName());
        }
        return names;
    }
}
