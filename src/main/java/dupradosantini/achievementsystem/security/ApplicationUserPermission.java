package dupradosantini.achievementsystem.security;

import lombok.Getter;

@Getter
public enum ApplicationUserPermission {
    //PERMISSIONS ENUM
    PLAYER_READ("player:read"),
    PLAYER_WRITE("player:write"),
    PLAYER_DELETE("player:delete"),
    GAMES_READ("games:read"),
    GAMES_WRITE("games:write");

    //Permissions field
    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }
}
