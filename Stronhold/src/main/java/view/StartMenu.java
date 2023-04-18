package view;

import java.util.Scanner;
import java.util.regex.Matcher;

public class StartMenu {
    public void run(Scanner scanner){
        String input=scanner.nextLine().trim();

    }

    public void checkWhichMenu(Matcher matcher,Scanner scanner){
        if(true){
            System.out.println("repair");
            SignUpMenu signUpMenu=new SignUpMenu();
            signUpMenu.run(scanner);
        }else if(true){
            System.out.println("repair");
            LoginMenu loginMenu=new LoginMenu();
            loginMenu.run(scanner);
        }
    }
}
