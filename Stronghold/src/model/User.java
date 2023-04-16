package model;

public class User {
    private String username, password, nickname, email, slogan;
    int highScore;

    public User(String username,String password,String nickName,String email,String slogan){
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickName;
        this.slogan = slogan;
        this.highScore = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getSlogan() {
        return slogan;
    }

    public int getHighScore() {
        return highScore;
    }
}
