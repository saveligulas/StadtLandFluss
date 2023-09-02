package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.CSV2PostgreSQL.model.DynamicField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicFieldRepository extends JpaRepository<DynamicField, Long> {
}
