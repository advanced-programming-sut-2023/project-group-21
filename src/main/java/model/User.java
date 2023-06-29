package model;

import model.generalenums.MessageEnum;

import java.util.ArrayList;

public class User {
    String userName, password, nickName, email, slogan, passwordRecoveryAnswer,pictureName;
    private final ArrayList<Chat> chats = new ArrayList<>();

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat){
        chats.add(chat);
    }

    public User(String userName, String password, String nickName, String email, String slogan) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.slogan = slogan;
        pictureName="pic1.jpg";
        Chat chat = new Chat(this, MessageEnum.PRIVATE_CHAT);
        chats.add(chat);
    }

    public String getPasswordRecoveryAnswer() {
        return passwordRecoveryAnswer;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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

    public String getPictureName() {
        return pictureName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setPasswordRecoveryAnswer(String passwordRecoveryAnswer) {
        this.passwordRecoveryAnswer = passwordRecoveryAnswer;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}
