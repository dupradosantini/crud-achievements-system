package dupradosantini.achievementsystem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L; //serialization ID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Which game has this achievement...
    private Integer gameId;

    @NotEmpty(message = "Campo nome é obrigatório")
    @Length(min = 3, max = 30, message = "O nome deve entre 3 e 30 carateres")
    private String name;

    @NotEmpty(message = "Campo descrição é obrigatório")
    @Length(min = 3, max = 50, message = "A descrição deve ter entre 3 e 50 carateres")
    private String description;

    @Length(max=50, message = "O URL para a imagem da conquista deve ter no máximo 50 caracteres")
    private String picture;

    public Achievement(Integer gameId, String name, String description, String picture) {
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }
    //Construtor para picture null
    public Achievement(Integer gameId, String name, String description) {
        this.gameId = gameId;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return getId().equals(that.getId()) && getGameId().equals(that.getGameId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGameId());
    }
}
