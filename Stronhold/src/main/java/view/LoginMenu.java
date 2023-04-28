package view;

import controller.OtherController;
import controller.FileController;
import controller.LoginMenuController;
import model.User;
import view.commands.CheckValidion;
import view.commands.LoginCommands;
import view.message.LoginMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    LoginMenuController loginMenuController = new LoginMenuController();


    public void run(Scanner scanner) {
        String line;
        Matcher matcher;
        while (true) {
            line = scanner.nextLine();
            OtherController.sleepShort();
            if ((matcher = LoginCommands.getMatcher(line, LoginCommands.BACK)) != null)
                break;
            else if ((matcher = LoginCommands.getMatcher(line, LoginCommands.LOGIN)) != null)
                checkLogin(matcher, scanner);
            else if ((matcher = LoginCommands.getMatcher(line, LoginCommands.FORGET_PASSWORD)) != null)
                checkForgotPassword(matcher, scanner);
            else if ((matcher = LoginCommands.getMatcher(line, LoginCommands.FORGET_USERNAME)) != null)
                System.out.println("repair");
            else
                System.out.println("Invalid format");
        }
    }

    public void checkLogin(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        LoginMessages loginMessages = loginMenuController.CheckLogin(username, password);
        if (loginMessages == LoginMessages.NO_USER_EXISTS)
            System.out.println("No user with this this id exists!");
        else if (loginMessages == LoginMessages.INCORRECT_PASSWORD)
            System.out.println("Incorrect password");
        else {
            System.out.println(OtherController.generateCaptcha());
            String solve = scanner.nextLine();
            if (OtherController.checkCaptcha(solve)) {
                OtherController.clearScreen();
                OtherController.resetSleepTime();
                System.out.println("successful login you are in main menu");
                User user = loginMenuController.Login(username);
                PlayerMenu playerMenu = new PlayerMenu(user);
                playerMenu.run(scanner);
                return;
            }
            OtherController.increaseSleepRate();
            OtherController.clearScreen();
            System.out.println("wrong captcha!");
        }
    }

    public void checkForgotPassword(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        OtherController.sleepNormal();
        System.out.println("please enter the captcha!");
        System.out.println(OtherController.generateCaptcha());
        String input = scanner.nextLine();
        OtherController.sleepShort();
        if(!OtherController.checkCaptcha(input)){
            OtherController.clearScreen();
            System.out.println("Wrong captcha!");
            OtherController.increaseSleepRate();
            return;
        }
        OtherController.clearScreen();
        if (!FileController.checkExistenceOfUserOrEmail(username, true)) {
            System.out.println("no user with this username found!");
            return;
        }
        System.out.println(loginMenuController.getSecurityQuestion(username));
        String answer = scanner.nextLine();
        if(loginMenuController.changePassword(username,answer)==LoginMessages.INCORRECT_ANSWER) {
            System.out.println("incorrect answer");
            return;
        }
        System.out.println("now enter your password and confirm it");
        String line = scanner.nextLine();
        if((matcher = LoginCommands.getMatcher(line,LoginCommands.NEW_PASSWORD))!=null){
            String password = matcher.group("password");
            String confirm = matcher.group("confirm");
            if(password.equals(confirm)){
                if(CheckValidion.check(password,CheckValidion.CHECK_PASSWORD)) {
                    System.out.println("password changed successfully!");
                    if(matcher.group("last")!=null)
                        FileController.changeStayed(username);
                    FileController.modifyInfo("password", username, password);
                }else
                    System.out.println("weak password");
            }else
                System.out.println("password and confirm are not the same!");
        }else
            System.out.println("invalid format for new password");
    }
}