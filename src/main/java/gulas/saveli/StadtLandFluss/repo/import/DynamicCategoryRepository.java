package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.CSV2PostgreSQL.model.DynamicCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DynamicCategoryRepository extends JpaRepository<DynamicCategory, Long> {
    @Query("SELECT d FROM DynamicCategory d WHERE d.name = ?1")
    Optional<DynamicCategory> findByName(String name);
}
