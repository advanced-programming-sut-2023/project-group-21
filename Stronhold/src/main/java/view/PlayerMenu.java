package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import controller.FileController;
import controller.MapController;
import model.Cell;
import model.Government;
import model.User;
import view.commands.PlayerMenuCommand;


public class PlayerMenu {
    private final User user;
    private Cell[][] myMap;
    private MapMenu mapMenu;

    public PlayerMenu(User user) {
        this.user = user;
    }

    public void run(Scanner scanner) {
        String line ;
        Matcher matcher;
        while (true) {
            line = scanner.nextLine();
            if (line.equals("logout"))
                break;
            else if((matcher = PlayerMenuCommand.getMatcher(line,PlayerMenuCommand.GO_TO_MENU))!=null)
                checkMenu(matcher,scanner);
            else if(line.equals("help"))
                help();
        }
    }

    private void help() {
        System.out.println("You can go to profile, map or game menu. Also you can logout by inputting so");
    }


    public void checkMenu(Matcher matcher, Scanner scanner) {
        String menuName = matcher.group("menuName");
        switch (menuName) {
            case "profile" -> {
                System.out.println("you are in profile menu");
                ProfileMenu profileMenu = new ProfileMenu(user);
                profileMenu.run(scanner);
            }
            case "map" -> {
                System.out.println("you are in map menu");
                mapMenu = new MapMenu(user);
                mapMenu.run(scanner);
                myMap = mapMenu.getMyMap();
            }
            case "game" -> {
                if(myMap==null && FileController.checkExistenceOfMap(user.getUserName())){
                    System.out.println("you should first make the map!");
                    return;
                }
                if(mapMenu==null){
                    mapMenu = new MapMenu(user);
                }
                System.out.println("first enter number of player and then enter their username");
                int numberOfPlayer;
                try {
                    numberOfPlayer = Integer.parseInt(scanner.nextLine());
                    ArrayList<Government> governments = new ArrayList<>();
                    Government government = new Government(user);
                    governments.add(government);
                    for (int i1=1;i1<numberOfPlayer;i1++){
                        String username1 = scanner.nextLine();
                        User tempUser = FileController.getUserByUsername(username1);
                        Government tempGovernment;
                        if (tempUser == null){
                            System.out.println("no user found,please try again!");
                            return;
                        }
                        tempGovernment = new Government(tempUser);
                        governments.add(tempGovernment);
                        GameMenu gameMenu = new GameMenu(user,governments);
                        gameMenu.setMapMenu(mapMenu);
                        gameMenu.run(scanner);
                    }
                }catch (NumberFormatException e){
                    System.out.println("please enter a number");
                }
            }

        }
    }

}
