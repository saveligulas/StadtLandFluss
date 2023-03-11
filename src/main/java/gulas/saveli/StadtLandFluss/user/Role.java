package gulas.saveli.StadtLandFluss.user;

import lombok.Getter;

import java.util.List;

@Getter
public enum Role {
    USER(new Authority[]{Authority.USER_AUTHORITIES}), ADMIN(new Authority[]{Authority.USER_AUTHORITIES, Authority.EDIT_USERS, Authority.EDIT_LIBRARY});

    private final List<Authority> AUTHORITY_LIST;

    Role(Authority[] AUTHORITY_ARRAY) {
        this.AUTHORITY_LIST = List.of(AUTHORITY_ARRAY);
    }
}
