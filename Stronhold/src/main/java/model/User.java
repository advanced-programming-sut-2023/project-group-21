package model;

public class User {
    String userName, passWord, nickName, email, slogan, passwordRecoveryAnswer;

    public User(String userName, String passWord, String nickName, String email, String slogan) {
        this.userName = userName;
        this.passWord = passWord;
        this.nickName = nickName;
        this.email = email;
        this.slogan = slogan;

    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getSlogan() {
        return slogan;
    }

}
