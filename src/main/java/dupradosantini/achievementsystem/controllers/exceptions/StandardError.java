package dupradosantini.achievementsystem.controllers.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardError {

    private  Integer status;
    private  Long timestamp;
    private String message;

    public StandardError(){
        super();
    }

    public StandardError(Integer status, Long timestamp, String message) {
        super();
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
    }

}
