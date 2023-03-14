package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.StadtLandFluss.game.logic.saves.SaveGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveGameRepository extends JpaRepository<SaveGame, Long> {
}
