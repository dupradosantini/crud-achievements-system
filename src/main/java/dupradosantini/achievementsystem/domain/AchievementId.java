package dupradosantini.achievementsystem.domain;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
public class AchievementId implements Serializable {

    private static final long serialVersionUID = 1L; //serialization ID

    private Integer id;

    private Integer ownedByGameId;

    public AchievementId(Integer id, Integer ownedByGameId) {
        this.id = id;
        this.ownedByGameId = ownedByGameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AchievementId)) return false;
        AchievementId that = (AchievementId) o;
        return id.equals(that.id) && ownedByGameId.equals(that.ownedByGameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownedByGameId);
    }
}
