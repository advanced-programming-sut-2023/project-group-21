package model;

public class User {
    String userName, passWord, nickName, email, slogan, passwordRecoveryAnswer;
    int passwordRecoveryQuestionIndex, highScore;

    public User(String userName, String passWord, String nickName, String email, String slogan,String passwordRecoveryAnswer, int passwordRecoveryQuestionIndex) {
        this.userName = userName;
        this.passWord = passWord;
        this.nickName = nickName;
        this.email = email;
        this.slogan = slogan;
        this.passwordRecoveryQuestionIndex = passwordRecoveryQuestionIndex;
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
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

    public int getPasswordRecoveryQuestionIndex() {
        return passwordRecoveryQuestionIndex;
    }

    public int getHighScore() {
        return highScore;
    }
}
