package view;

import controller.FileController;
import controller.OtherController;
import controller.SignUpController;
import model.Game;
import model.User;
import view.commands.SignUpCommands;
import view.message.SignUpMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignUpMenu {
    SignUpController signUpController = new SignUpController();


    public void run(Scanner scanner) {
        String line;
        Matcher matcher;
        while (true) {
            OtherController.sleepShort();
            line = scanner.nextLine();
            line = line.trim();
            if (line.equals("back"))
                break;
            else if ((matcher = SignUpCommands.getMatcher(line, SignUpCommands.REGISTER_WITH_PASSWORD)) != null)
                checkCreateUser(matcher, scanner);
            else if ((matcher = SignUpCommands.getMatcher(line, SignUpCommands.REGISTER_WITH_RANDOM_PASSWORD)) != null)
                checkCreateUserPassword(matcher, scanner);
            else if(line.equals("help"))
                help();
            else
                System.out.println("invalid command!");
        }
    }

    private void help() {
        System.out.println("You are in the signup menu\nYou can register with random or selected password");
    }


    public void doAction(Scanner scanner, Matcher matcher, String username, String password, String email,
                         String nickname, String slogan) {
        String answer;
        String AnswerConfirm;
        int questionNumber = 0;
        checkPickQuestion();
        String line = scanner.nextLine();
        if ((matcher = SignUpCommands.getMatcher(line, SignUpCommands.PICK_QUESTION)) == null) {
            System.out.println("invalid format for security question");
            return;
        }
        String strNumber = matcher.group("number");
        try {
            questionNumber = Integer.parseInt(strNumber);
        } catch (NumberFormatException e) {
            System.out.println("enter an integer not string or float");
        }
        answer = matcher.group("answer");
        AnswerConfirm = matcher.group("confirm");
        SignUpMessages signUpMessages = signUpController.pickQuestion(questionNumber, answer, AnswerConfirm);
        if (signUpMessages == SignUpMessages.OUT_OF_RANGE)
            System.out.println("out of range");
        else if (signUpMessages == SignUpMessages.BAD_REPEAT)
            System.out.println("answer and confirm are not the same");
        else {
            if (slogan != null && slogan.equals("random")) {
                slogan = signUpController.giveRandomSlogan();
                System.out.println("your slogan is: " + slogan);
            }
            System.out.println(OtherController.generateCaptcha());
            String solve = scanner.nextLine().trim();
            if(OtherController.checkCaptcha(solve)) {
                OtherController.resetSleepTime();
                OtherController.clearScreen();
                User user;
                System.out.println("registered successfully");
                System.out.println("you are in player menu");
                user = signUpController.createUser(username, password, email, nickname, slogan, questionNumber, answer);
                PlayerMenu playerMenu = new PlayerMenu(user);
                playerMenu.run(scanner);
                return;
            }
            OtherController.clearScreen();
            System.out.println("captcha mistake please try again!");
            OtherController.increaseSleepRate();
        }
    }


    public void checkCreateUser(Matcher matcher, Scanner scanner) {

        String username = matcher.group("username");
        String password = matcher.group("password");
        String confirm = matcher.group("confirm");
        String email = matcher.group("email");
        String slogan = matcher.group("slogan");
        String nickname = matcher.group("nickname");
        SignUpMessages signUpMessages = signUpController.checkValidationFormat(email, username, password, confirm);
        System.out.println(signUpMessages.toString());
        if (signUpMessages != SignUpMessages.PRINT_NOTHING)
            return;
        if (signUpController.createUserLastCheck(username, password, email, nickname,
                slogan) == SignUpMessages.EMAIL_REPEAT)
            System.out.println("please enter another email , this email is already used");
        else if (signUpController.createUserLastCheck(username, password, email, nickname,
                slogan) == SignUpMessages.USERNAME_REPEAT)
            System.out.println("this username is already taken but this username is available "+ FileController.suggestUsername(username));
        else
            doAction(scanner, matcher, username, password, email, nickname, slogan);
    }

    public void checkCreateUserPassword(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        String email = matcher.group("email");
        String slogan = matcher.group("slogan");
        String nickname = matcher.group("nickname");
        String password;
        String confirm;
        SignUpMessages signUpMessages = signUpController.checkValidationFormat(email, username, "Aa!1234", "Aa!1234");
        System.out.println(signUpMessages.toString());
        if (signUpMessages != SignUpMessages.PRINT_NOTHING)
            return;
        if (signUpController.createUserLastCheck(username, null, email, nickname,
                slogan) == SignUpMessages.USERNAME_REPEAT)
            System.out.println("this username is already taken");
        else {
            password = signUpController.generateRandomPassword();
            System.out.println("your password is " + password + " please confirm it");
            confirm = scanner.nextLine();
            if (!confirm.equals(password)) {
                System.out.println("invalid repeat format please try again");
                return;
            }
            doAction(scanner, matcher, username, password, email, nickname, slogan);
        }
    }

    private void checkPickQuestion() {
        System.out.println("pick your security question");
        for (int i1 = 0; i1 < Game.SECURITY_QUESTION.length; i1++)
            System.out.println((i1 + 1) + " " + Game.SECURITY_QUESTION[i1]);
    }
}