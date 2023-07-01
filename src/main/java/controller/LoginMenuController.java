package controller;



import model.Game;
import ServerConnection.User;
import view.message.LoginMessages;

import java.io.IOException;


public class LoginMenuController {


    public LoginMessages CheckLogin(String username, String password) throws IOException, InterruptedException, ClassNotFoundException {
        password = FileController.encode(password);
        if (!FileController.checkExistenceOfUserOrEmail(username, true)) {
            OtherController.increaseSleepRate();
            return LoginMessages.NO_USER_EXISTS;
        }
        if (!password.equals(FileController.getInfo(username, "password"))) {
            OtherController.increaseSleepRate();
            return LoginMessages.INCORRECT_PASSWORD;
        }
        OtherController.sleepNormal();
        return LoginMessages.SUCCESS;
    }


    public User Login(String username) {
        return FileController.getUserByUsername(username);
    }

    public String getSecurityQuestion(String username) {
        return Game.SECURITY_QUESTION[(FileController.getSecurityQuestion(username) % 3)];
    }



    public LoginMessages changePassword(String username, String answer) {
        answer = FileController.encode(answer);
        if (answer.equals(FileController.getInfo(username, "answer"))) {
            OtherController.resetSleepTime();
            return LoginMessages.SUCCESS;
        }
        OtherController.increaseSleepRate();
        return LoginMessages.INCORRECT_ANSWER;
    }
}
