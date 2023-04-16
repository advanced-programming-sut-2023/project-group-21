package view;

import java.util.Scanner;
import java.util.regex.Matcher;

public class PlayerMenu {
    public void run(Scanner scanner){

    }

    public void checkMenu(Matcher matcher,Scanner scanner){
        if(true){
            System.out.println("repair");
            ProfileMenu profileMenu=new ProfileMenu();
            profileMenu.run();
        }else if(true){
            System.out.println("repair");
            MapMenu mapMenu=new MapMenu();
            mapMenu.run(scanner);
        }else if(true){
            System.out.println("repair");
            GameMenu gameMenu=new GameMenu();
            gameMenu.run(scanner);
        }
    }

}
