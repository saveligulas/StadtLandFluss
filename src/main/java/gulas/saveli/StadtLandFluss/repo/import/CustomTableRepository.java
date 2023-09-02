package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.CSV2PostgreSQL.model.CustomTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomTableRepository extends JpaRepository<CustomTable, Long> {
    @Query("SELECT c FROM CustomTable c WHERE c.name = ?1")
    Optional<CustomTable> findByName(String name);
}
