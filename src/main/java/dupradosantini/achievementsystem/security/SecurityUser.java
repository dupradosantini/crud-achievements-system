package dupradosantini.achievementsystem.security;

import dupradosantini.achievementsystem.domain.Player;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static dupradosantini.achievementsystem.security.ApplicationUserRole.ADMIN;

public class SecurityUser implements UserDetails {

    private final Player player;

    public SecurityUser(Player player) {
        this.player = player;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ADMIN.getGrantedAuthorities();
        //if player id =1 admin, else player.
    }

    @Override
    public String getPassword() {
        return player.getPassword();
    }

    @Override
    public String getUsername() {
        return player.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
