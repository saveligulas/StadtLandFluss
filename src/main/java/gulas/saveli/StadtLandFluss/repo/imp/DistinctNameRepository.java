package gulas.saveli.StadtLandFluss.repo.imp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistinctNameRepository extends JpaRepository<DistinctName, Long> {
    @Query("SELECT d FROM DistinctName d WHERE d.modelType = ?1 AND d.name = ?2")
    Optional<DistinctName> findByTypeAndName(ModelType type, String name);
}
