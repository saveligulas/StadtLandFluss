package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.StadtLandFluss.game.models.AnswerList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerListRepository extends JpaRepository<AnswerList, Long> {
}
