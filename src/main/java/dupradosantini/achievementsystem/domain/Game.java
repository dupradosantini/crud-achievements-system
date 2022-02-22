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
public class Game implements Serializable {

    private static final long serialVersionUID = 1L; //serialization ID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Campo nome é obrigatório")
    @Length(min = 3, max = 30, message = "O nome deve entre 3 e 30 carateres")
    private String name;

    @Length(max=50, message = "O URL para a imagem publicitária deve ter no máximo 50 caracteres")
    private String coverImage;

    @NotEmpty
    @Length(min = 2, max=15, message = "O genero deve ter entre 2 e 15 caracteres")
    private String genre;

    //ManyToMany Relationship with Player
    @ManyToMany(mappedBy = "ownedGames")
    private Set<Player> players;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Set<Achievement> achievements;

    public Game(String name, String coverImage, String genre) {
        this.name = name;
        this.coverImage = coverImage;
        this.genre = genre;
    }

    public Game(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return getId().equals(game.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
