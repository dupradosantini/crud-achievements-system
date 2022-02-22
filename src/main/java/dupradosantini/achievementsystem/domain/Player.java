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

@Table(indexes = @Index(columnList = "email"))
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Player implements Serializable {

    private static final long serialVersionUID = 1L; //serialization ID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Campo nome é obrigatório")
    @Length(min = 3, max = 30, message = "O nome deve entre 3 e 30 carateres")
    private String name;

    @NotEmpty(message = "Campo email é obrigatório")
    @Length(max = 30, message = "O email deve ter no máximo 30 caracteres")
    private String email;

    @Length(max=50, message = "O URL para a foto de perfil deve ter no máximo 50 caracteres")
    private String profilePic;

    //Relationship with Games
    @ManyToMany
    @JoinTable(name="ownership",
                joinColumns = @JoinColumn(name = "player_id"),
                inverseJoinColumns = @JoinColumn(name="game_id"))
    private Set<Game> ownedGames;

    public Player(String name, String email, String profilePic) {
        this.name = name;
        this.email = email;
        this.profilePic = profilePic;
    }
    //Construttor sem foto de perfil.
    public Player(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getId().equals(player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }
}
