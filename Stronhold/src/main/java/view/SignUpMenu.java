package view;

import controller.SignUpController;
import model.User;
import view.commands.CheckValidion;
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
            line = scanner.nextLine();
            line = line.trim();
            if (line.equals("back"))
                break;
            else if ((matcher = SignUpCommands.getMatcher(line, SignUpCommands.REGISTER_WITH_PASSWORD)) != null)
                checkCreateUser(matcher, scanner);
            else if ((matcher = SignUpCommands.getMatcher(line, SignUpCommands.REGISTER_WITH_RANDOM_PASSWORD)) != null)
                checkCreateUserPassword(matcher, scanner);
        }
    }

    public void doAction(Scanner scanner, Matcher matcher, String username, String password, String email,
            String nickname, String slogan) {
        String answer = "";
        String confirmQuestion = "";
        int questionNumber = 0;
        checkPickQuestion();
        String line = scanner.nextLine();
        if ((matcher = SignUpCommands.getMatcher(line, SignUpCommands.PICK_QUESTION)) != null) {
            String strNumber = matcher.group("questionNnmber");
            try {
                questionNumber = Integer.parseInt(strNumber);
            } catch (NumberFormatException e) {
                System.out.println("enter an integer not string or float");
            }
            answer = matcher.group("answer");
            confirmQuestion = matcher.group("confirm");
            SignUpMessages signUpMessages = signUpController.pickQuestion(questionNumber, answer, confirmQuestion);
            if (signUpMessages == SignUpMessages.OUT_OF_RANGE)
                System.out.println("out of range");
            else if (signUpMessages == SignUpMessages.BAD_REAPAET)
                System.out.println("answer and confirm are not the same");
            else {
                if (slogan.equals("random")) {
                    slogan = signUpController.giveRandomSlogan();
                    System.out.println("your slogan is:" + slogan);
                }
                User user = new User(username, password, nickname, email, slogan);
                PlayerMenu playerMenu = new PlayerMenu(user);
                System.out.println("registered successfully");
                System.out.println("you are in player menu");
                signUpController.createUser(username, password, email, nickname, slogan, questionNumber, answer);
                playerMenu.run(scanner);
            }
        }
    }

    public void checkCreateUser(Matcher matcher, Scanner scanner) {

        String username = matcher.group("username");
        String password = matcher.group("password");
        String confirm = matcher.group("confirm");
        String email = matcher.group("email");
        String slogan = matcher.group("slogan");
        String nickname = matcher.group("nickname");

        if (email == null)
            System.out.println("please enter your email");
        else if (!confirm.equals(password))
            System.out.println("password and confirm are not the same");
        else if (!CheckValidion.check(username, CheckValidion.CHECK_USERNAME))
            System.out.println("please enter valid username");
        else if (!CheckValidion.check(password, CheckValidion.CHECK_PASSWORD))
            System.out.println("weak password");
        else if (!CheckValidion.check(email, CheckValidion.CHECK_EMAIL))
            System.out.println("please enter valid email format");
        else if (signUpController.createUserLastCheck(username, password, email, nickname,
                slogan) == SignUpMessages.EMAIL_REPEAT)
            System.out.println("please enter another email , this email is already used");
        else if (signUpController.createUserLastCheck(username, password, email, nickname,
                slogan) == SignUpMessages.USERNAME_REPEAT)
            System.out.println("this username is already taken");
        else
            doAction(scanner, matcher, username, password, email, nickname, slogan);
    }

    public void checkCreateUserPassword(Matcher matcher, Scanner scanner) {
        String username = matcher.group("username");
        String email = matcher.group("email");
        String slogan = matcher.group("slogan");
        String nickname = matcher.group("nickname");
        String password = "";
        String confirm = "";
        if (!CheckValidion.check(username, CheckValidion.CHECK_USERNAME))
            System.out.println("please enter valid username");
        else if (!CheckValidion.check(email, CheckValidion.CHECK_EMAIL))
            System.out.println("please enter valid email format");
        else if (signUpController.createUserLastCheck(username, null, email, nickname,
                slogan) == SignUpMessages.EMAIL_REPEAT)
            System.out.println("please enter another email , this email is already used");
        else if (signUpController.createUserLastCheck(username, null, email, nickname,
                slogan) == SignUpMessages.USERNAME_REPEAT)
            System.out.println("this username is already taken");
        else {
            password = signUpController.generateRandomPassword();
            System.out.println("your password is " + password + " please confirm it");
            confirm = scanner.nextLine();
            if (!confirm.equals(password)) {
                System.out.println("invalid repeat format please try agian");
                return;
            }
            doAction(scanner, matcher, username, password, email, nickname, slogan);
        }
    }

    private void checkPickQuestion() {
        System.out.println("pick your security question");
        System.out.println("1 what is your favorite food ?");
        System.out.println("2 what is your father s name ?");
        System.out.println("3 what is your oldest sibling's first name?");
    }

}