package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.StadtLandFluss.game.models.AnswerList;
import gulas.saveli.StadtLandFluss.game.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerListRepository extends JpaRepository<AnswerList, Long> {

    @Query("SELECT a FROM AnswerList a WHERE a.name = ?1")
    public Optional<AnswerList> findByName(String name);
}
