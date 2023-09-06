package gulas.saveli.StadtLandFluss.repo.imp;

import gulas.saveli.StadtLandFluss.game.models.checker.DynamicField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicFieldRepository extends JpaRepository<DynamicField, Long> {
}
