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
    public void run(Scanner scanner){
        String line;
        Matcher matcher;
        while (true){
            line = scanner.nextLine();
            line = line.trim();
            if(line.equals("back"))
                break;
            else if((matcher= SignUpCommands.getMatcher(line,SignUpCommands.REGISTER_WITH_PASSWORD))!=null)
                checkCreateUser(matcher,scanner);
            else if((matcher =SignUpCommands.getMatcher(line,SignUpCommands.REGISTER_WITH_RANDOM_PASSWORD))!= null)
                checkCreateUserPassword(matcher,scanner);
        }
    }

    public void checkCreateUser(Matcher matcher,Scanner scanner){
        String username = matcher.group("username");
        String password = matcher.group("password");
        String confirm = matcher.group("confirm");
        String email = matcher.group("email");
        String slogan = matcher.group("slogan");
        String nickname = matcher.group("nickname");
        String answer = "";
        int questionNumber = 0;
        if(email == null)
            System.out.println("please enter your email");
        else if(!CheckValidion.check(username,CheckValidion.CHECK_USERNAME))
            System.out.println("please enter valid username");
        else if(!CheckValidion.check(password,CheckValidion.CHECK_PASSWORD))
            System.out.println("weak password");
        else if(!CheckValidion.check(email,CheckValidion.CHECK_EMAIL))
            System.out.println("please enter valid email format");
        else if(signUpController.createUserLastCheck(username,password,email,nickname,slogan) == SignUpMessages.EMAIL_REPEAT)
            System.out.println("please enter another email , this email is already used");
        else if(signUpController.createUserLastCheck(username,password,email,nickname,slogan)==SignUpMessages.USERNAME_REPEAT)
            System.out.println("this username is already taken");
        else {
            checkPickQuestion();
            try {
                questionNumber = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("enter an int");
                return;
            }
            if (questionNumber <= 3 && questionNumber > 0) {
                System.out.println("what is your answer?");
                answer = scanner.nextLine();
                User user = new User(username,password,nickname,email,slogan);
                PlayerMenu playerMenu = new PlayerMenu(user);
                System.out.println("you are in player menu");
                playerMenu.run(scanner);
            }else
                System.out.println("out of range");
        }
    }
    public void checkCreateUserPassword(Matcher matcher,Scanner scanner){

    }

    public void checkPickQuestion(){
        System.out.println("pick your security question");
        System.out.println("1 what is your favorite food ?");
        System.out.println("2 what is your father s name ?");
        System.out.println("3 what is your oldest sibling's first name?");
    }


}
