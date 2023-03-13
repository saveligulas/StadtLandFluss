package gulas.saveli.StadtLandFluss.security.auth.disconnect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "DISCONNECTED_TOKENS")
public class InvalidToken {
    @Id
    private Long id;
    private String token;
}
