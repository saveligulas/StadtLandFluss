package gulas.saveli.StadtLandFluss.security.logger;

import gulas.saveli.StadtLandFluss.repo.ObjectRepository;
import gulas.saveli.StadtLandFluss.repo.UserRepository;
import gulas.saveli.StadtLandFluss.user.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserLogRepository implements ObjectRepository<String> {
    private final Map<String, String> repository;

    public UserLogRepository() {
        this.repository = new HashMap<>();
    }


    @Override
    public void store(String token, String email) {
        this.repository.put(token, email);
    }

    @Override
    public String retrieve(String token) {
        return repository.get(token);
    }

    @Override
    public boolean search(String requestEmail) {
        for(String email:repository.entrySet().toArray(new String[repository.size()])) {
            if(email.equals(requestEmail)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String delete(String token) {
        repository.remove(token);
        return null;
    }

    public List<String> retrieveUsers() {
        return List.copyOf(repository.values());
    }

    public List<String> retrieveTokens() {
        return List.copyOf(repository.keySet());
    }
}
