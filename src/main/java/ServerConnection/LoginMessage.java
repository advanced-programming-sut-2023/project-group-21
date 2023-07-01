package ServerConnection;

import java.io.Serializable;

public record LoginMessage(String username, String password) implements Serializable {
}
