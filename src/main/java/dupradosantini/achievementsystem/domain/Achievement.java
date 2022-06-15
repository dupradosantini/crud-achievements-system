package dupradosantini.achievementsystem.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity(name = "Achievement")
@Table(name = "achievement")
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L; //serialization ID


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Campo nome é obrigatório")
    @Length(min = 3, max = 30, message = "O nome deve entre 3 e 30 carateres")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "Campo descrição é obrigatório")
    @Length(min = 3, max = 50, message = "A descrição deve ter entre 3 e 50 carateres")
    private String description;

    @Column(name = "picture")
    @Length(max=300, message = "O URL para a imagem da conquista deve ter no máximo 50 caracteres")
    private String picture;

    @ManyToOne(optional = false)
    @JsonBackReference(value = "game-achievement")
    //@JsonIgnore
    private Game game;

    @ManyToMany(mappedBy = "unlockedAchievements")
    @JsonBackReference(value = "player-achievement")
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
                ", gameId=" + game.getId() +
                '}';
    }
    //Funcao para passar o Id do jogo correspondente.
    public Integer getGameId() {
        return this.game.getId();
    }
}
