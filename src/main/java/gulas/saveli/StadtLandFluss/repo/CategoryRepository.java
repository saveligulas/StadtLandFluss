package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.StadtLandFluss.game.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> { //TODO: remove this repository when removing category entity
    @Query("SELECT c FROM Category c WHERE c.name = ?1")
    public Optional<Category> findByName(String name);
}
