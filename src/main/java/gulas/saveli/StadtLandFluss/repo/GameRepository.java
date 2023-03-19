package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.StadtLandFluss.game.logic.model.Game;
import gulas.saveli.StadtLandFluss.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g WHERE g.hostUsername = ?1")
    Optional<Game> findByHostUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GAME", nativeQuery = true)
    void deleteAllData();
}
