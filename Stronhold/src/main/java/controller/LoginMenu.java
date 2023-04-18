package controller;

import model.Game;
import model.User;
import view.message.LoginMessages;

public class LoginMenu {
    private User loggedInUser;
    public LoginMessages login(String username,String password){
        if((loggedInUser = Game.getUserByUsername(username)) == null) return null;
        if (!password.equals(loggedInUser.getUserName())) return null;
        return null;
    }

    public int forgotPassword(String username){
        if ((loggedInUser = Game.getUserByUsername(username)) == null) return 0;
        return loggedInUser.getPasswordRecoveryQuestionIndex();
    }
}
