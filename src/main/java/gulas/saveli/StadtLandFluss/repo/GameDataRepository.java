package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.StadtLandFluss.game.logic.model.GameData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDataRepository extends JpaRepository<GameData, Long> {
}
