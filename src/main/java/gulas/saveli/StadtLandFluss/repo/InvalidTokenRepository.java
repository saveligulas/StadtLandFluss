package gulas.saveli.StadtLandFluss.repo;

import gulas.saveli.StadtLandFluss.security.auth.disconnect.InvalidToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM DISCONNECTED_TOKENS", nativeQuery = true)
    void deleteAllData();
}
