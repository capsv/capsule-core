package auth.service.dev.common;

import lombok.Getter;

@Getter
public enum Role {

    /*
     *
     * normal (600-700 points)
     * mild (500-600 points)
     * suspicious (400-500 points)
     * significant (300-400 points)
     * emergent (200-300 points)
     * harsh (100-200 points)
     * severe (0-100 points)
     *
     */

    GUEST("unauthorized user", "GUEST"),
    USER ("an authorized user without a completed test", "USER"),
    USER_SEVERE("authorized user with 1 rank","USER_SEVERE"),
    USER_HARSH("authorized user with 2 rank","USER_HARSH"),
    USER_EMERGENT("authorized user with 3 rank","USER_EMERGENT"),
    USER_SIGNIFICANT("authorized user with 4 rank","USER_SIGNIFICANT"),
    USER_SUSPICIOUS("authorized user with 5 rank","USER_SUSPICIOUS"),
    USER_MILD("authorized user with 6 rank","USER_MILD"),
    USER_NORMAL("authorized user with 7 rank","USER_NORMAL"),
    ADMIN("an authorized user with special rights","ADMIN");

    private final String description;

    private final String tag;

    Role(String description, String tag){
        this.description=description;
        this.tag=tag;
    }

}
