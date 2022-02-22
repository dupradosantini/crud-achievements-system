package dupradosantini.achievementsystem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@IdClass(AchievementId.class)
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L; //serialization ID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Which game has this achievement...
    @Id
    private Integer ownedByGameId;

    @NotEmpty(message = "Campo nome é obrigatório")
    @Length(min = 3, max = 30, message = "O nome deve entre 3 e 30 carateres")
    private String name;

    @NotEmpty(message = "Campo descrição é obrigatório")
    @Length(min = 3, max = 50, message = "A descrição deve ter entre 3 e 50 carateres")
    private String description;

    @Length(max=50, message = "O URL para a imagem da conquista deve ter no máximo 50 caracteres")
    private String picture;

    @ManyToOne
    private Game game;

    @ManyToMany(mappedBy = "unlockedAchievements")
    private Set<Player> players;


    public Achievement(Game game, String name, String description, String picture) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.game = game;
        this.ownedByGameId = game.getId();
    }
    //Construtor para picture null
    public Achievement(Game game, String name, String description) {
        this.name = name;
        this.description = description;
        this.game = game;
        this.ownedByGameId = game.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return getId().equals(that.getId()) && this.getOwnedByGameId().equals(that.getOwnedByGameId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), this.getOwnedByGameId());
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", ownedByGameId=" + ownedByGameId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
