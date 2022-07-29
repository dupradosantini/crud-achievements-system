package dupradosantini.achievementsystem.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static dupradosantini.achievementsystem.security.ApplicationUserPermission.*;

@Getter
public enum ApplicationUserRole {
    //ROLES ENUM
    PLAYER(Sets.newHashSet(PLAYER_READ,GAMES_READ)),  //Lets start giving players only read permissions
    ADMIN(Sets.newHashSet(PLAYER_READ,PLAYER_WRITE,GAMES_READ,GAMES_WRITE));

    //Permissions set of a role
    private final Set<ApplicationUserPermission> permissions; //Its a set because it has to be unique.

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }
}
