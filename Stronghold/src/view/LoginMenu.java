package view;

import controller.LoginMenuController;
import model.User;
import view.commands.LoginCommands;
import view.message.LoginMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    LoginMenuController loginMenuController = new LoginMenuController();
    public void run(Scanner scanner){
        String line = " ";
        Matcher matcher;
        while (true){
            line = scanner.nextLine();
            if((matcher = LoginCommands.getMatcher(line,LoginCommands.BACK))!= null)
                break;
            else if((matcher = LoginCommands.getMatcher(line,LoginCommands.LOGIN))!=null)
                checkLogin(matcher,scanner);
        }
    }

    public void checkLogin(Matcher matcher,Scanner scanner){
        String username = matcher.group("username");
        String password = matcher.group("password");
        LoginMessages loginMessages = loginMenuController.CheckLogin(username,password);
        if(loginMessages == LoginMessages.NO_USER_EXISTS)
            System.out.println("No user with this this id exists!");
        else if(loginMessages == LoginMessages.INCORRECT_PASSWORD)
            System.out.println("Incorrect password");
        else{
            System.out.println("successful login you are in main menu");
            User user = loginMenuController.Login(username);
            PlayerMenu playerMenu = new PlayerMenu(user);
            playerMenu.run(scanner);
        }
    }

    public void checkForgotPassword(Matcher matcher){

    }

}
