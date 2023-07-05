package ServerConnection;

import java.io.Serializable;

public record UserDetails(String username, String password, String email, String nickname, String slogan,
                          int questionNumber, String answer, String pictureName) implements Serializable {
}
