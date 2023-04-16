package model;

public class User {
    String userName, passWord, nickName, email, slogan;
    int passwordRecoveryQuestionIndex, highScore;

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

    public int getPasswordRecoveryQuestionIndex() {
        return passwordRecoveryQuestionIndex;
    }

    public int getHighScore() {
        return highScore;
    }
}
