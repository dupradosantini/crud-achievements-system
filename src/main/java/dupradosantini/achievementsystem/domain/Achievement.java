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
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L; //serialization ID


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Campo nome é obrigatório")
    @Length(min = 3, max = 30, message = "O nome deve entre 3 e 30 carateres")
    private String name;

    @NotEmpty(message = "Campo descrição é obrigatório")
    @Length(min = 3, max = 50, message = "A descrição deve ter entre 3 e 50 carateres")
    private String description;

    @Length(max=50, message = "O URL para a imagem da conquista deve ter no máximo 50 caracteres")
    private String picture;

    @ManyToOne(optional = false)
    private Game game;

    @ManyToMany(mappedBy = "unlockedAchievements")
    private Set<Player> players;


    public Achievement(Game game, String name, String description, String picture) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.game = game;
    }
    //Construtor para picture null
    public Achievement(Game game, String name, String description) {
        this.name = name;
        this.description = description;
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", game=" + game +
                '}';
    }
    //Funcao para passar o Id do jogo correspondente.
    public Integer getGameId() {
        return game.getId();
    }
}
