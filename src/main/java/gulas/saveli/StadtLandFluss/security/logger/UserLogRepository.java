package gulas.saveli.StadtLandFluss.security.logger;

import gulas.saveli.StadtLandFluss.repo.ObjectRepository;
import gulas.saveli.StadtLandFluss.repo.UserRepository;
import gulas.saveli.StadtLandFluss.user.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserLogRepository implements ObjectRepository<User> {
    private Map<String, User> repository;

    public UserLogRepository() {
        this.repository = new HashMap<>();
    }


    @Override
    public void store(String token, User user) {
        this.repository.put(token, user);
    }

    @Override
    public User retrieve(String token) {
        return repository.get(token);
    }

    @Override
    public User search(String name) {
        for(User user:repository.entrySet().toArray(new User[repository.size()])) {
            if(user.getFirstName().equals(name) || user.getLastName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User delete(String token) {
        return null;
    }

    public List<User> retrieveUsers() {
        return List.copyOf(repository.values());
    }

    public List<String> retrieveTokens() {
        return List.copyOf(repository.keySet());
    }

    public void deleteByName(String name) {
        for(User user:repository.entrySet().toArray(new User[repository.size()])) {
            if(user.getFullName().equals(name)) {
                repository.remove(user.getToken());
            }
        }
    }
}
