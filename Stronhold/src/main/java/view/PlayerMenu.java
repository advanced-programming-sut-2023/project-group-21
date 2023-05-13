package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import controller.FileController;

import controller.OtherController;
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
            else
                System.out.println("invalid format");
        }
    }

    private void help() {
        System.out.println("you are in player menu!\nYou can go to profile, map or game menu. Also you can logout by inputting so");
    }


    public void checkMenu(Matcher matcher, Scanner scanner) {
        ArrayList<Cell> myHolds;
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
                if(mapMenu==null){
                    mapMenu = new MapMenu(user);
                    myMap = mapMenu.getMyMap();
                }
                if(myMap==null){
                    System.out.println("no map found!\nplease try again!");
                    return;
                }
                myHolds = mapMenu.getMyHolds();
                System.out.println("first enter number of player and then enter their username");
                int numberOfPlayer;
                try {
                    numberOfPlayer = Integer.parseInt(scanner.nextLine().trim());
                    if(numberOfPlayer <2 || numberOfPlayer> 8 || numberOfPlayer> myHolds.size()){
                        System.out.println("invalid number");
                        return;
                    }
                    ArrayList<String> castlePlaces = OtherController.startTheGame(numberOfPlayer, myMap.length);
                    ArrayList<Government> governments = new ArrayList<>();
                    assert castlePlaces != null;
                    Government government = new Government(user,myHolds.get(0));
                    governments.add(government);
                    for (int i1=1;i1<numberOfPlayer;i1++){
                        String username1 = scanner.nextLine();
                        User tempUser = FileController.getUserByUsername(username1);
                        Government tempGovernment;
                        if (tempUser == null){
                            System.out.println("no user found,please try again!");
                            return;
                        }
                        tempGovernment = new Government(tempUser,myHolds.get(i1));
                        governments.add(tempGovernment);
                        GameMenu gameMenu = new GameMenu(user,governments,myMap);
                        gameMenu.setMapMenu(mapMenu);
                        gameMenu.run(scanner);
                    }
                }catch (NumberFormatException e){
                    System.out.println("please enter a number not String :)");
                }
            }

        }
    }

}
