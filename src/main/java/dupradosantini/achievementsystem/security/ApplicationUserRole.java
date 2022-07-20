package dupradosantini.achievementsystem.security;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;

import static dupradosantini.achievementsystem.security.ApplicationUserPermission.*;

@Getter
public enum ApplicationUserRole {
    //ROLES ENUM
    PLAYER(Sets.newHashSet()),  //Lets start giving players no permissions
    ADMIN(Sets.newHashSet(PLAYER_READ,PLAYER_WRITE,PLAYER_DELETE,GAMES_READ,GAMES_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(PLAYER_READ,GAMES_READ));

    //Permissions set of a role
    private final Set<ApplicationUserPermission> permissions; //Its a set because it has to be unique.

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
