package gulas.saveli.StadtLandFluss.security.auth.disconnect;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "DISCONNECTED_TOKENS")
public class InvalidToken {
    @Id
    @GeneratedValue
    private Long id;
    private String token;

}
