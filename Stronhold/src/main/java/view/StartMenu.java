package view;

import view.commands.StartMenuCommand;

import java.util.Scanner;
import java.util.regex.Matcher;

public class StartMenu {
    public void run(Scanner scanner){
        String input ;
        Matcher matcher;
        while (true){
            input = scanner.nextLine().trim();
            if(input.equals("exit"))
                break;
            else if((matcher = StartMenuCommand.getMatcher(input,StartMenuCommand.GO_TO_SIGNUP_MENU))!=null)
                checkWhichMenu(matcher,scanner,false);
            else if((matcher = StartMenuCommand.getMatcher(input,StartMenuCommand.GO_TO_SIGN_IN)) != null)
                checkWhichMenu(matcher,scanner,true);
            else if(input.equals("help"))
                help();
            else
                System.out.println("invalid format");
        }
    }

    private void help() {
        System.out.println("You are in the start menu:\n You can enter signup menu or login menu");
    }

    public void checkWhichMenu(Matcher matcher,Scanner scanner,boolean isLogin){
        if(!isLogin){
            System.out.println("you are in sign up menu");
            SignUpMenu signUpMenu=new SignUpMenu();
            signUpMenu.run(scanner);
            System.out.println("you are in start menu!");
        }else{
            System.out.println("you are in login menu");
            LoginMenu loginMenu=new LoginMenu();
            loginMenu.run(scanner);
            System.out.println("you are in start menu!");
        }
    }
}