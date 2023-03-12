package gulas.saveli.StadtLandFluss.security.logger;

import gulas.saveli.StadtLandFluss.errorHandler.handler.ApiRequestException;
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

    public void save(String token, User user) {
        List<String> tokens = userLogRepository.retrieveTokens();
        if(tokens.contains(token)) {
            throw new ApiRequestException("token already exists");
        }
        List<User> users = userLogRepository.retrieveUsers();
        if(users.contains(user)) {
            throw new ApiRequestException("user already exists");
        }
        userLogRepository.store(token, user);
    }

    public void delete(String token) {
        if(!userLogRepository.retrieveTokens().contains(token)) {
            for(User user : userLogRepository.retrieveUsers()) {
                if(user.getFullName().equals(token)) {
                    userLogRepository.deleteByName(token);
                    return;
                }
            }
            throw new ApiRequestException("token not found");
        }
        userLogRepository.delete(token);
    }

    public List<String> getConnectedUsersNames() {
        List<String> names = new ArrayList<>();
        for(User user:userLogRepository.retrieveUsers()) {
            names.add(user.getFullName());
        }
        return names;
    }
}
