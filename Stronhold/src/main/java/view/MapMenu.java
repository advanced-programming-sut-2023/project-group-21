package view;

import controller.MapController;
import model.Cell;
import model.User;
import view.commands.MapCommand;
import view.message.MapMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {

    private final User user;
    private final MapController mapController;

    public MapMenu(User user){
        this.user = user;
        mapController = new MapController();
    }
    public void run(Scanner scanner){
        String line;
        Matcher matcher;
        mapController.loadMap(user.getUserName());
        mapController.initializeMap(200,true);
        while (true){
            line = scanner.nextLine();
            if(line.equals("back"))
                break;
            else if((matcher = MapCommand.getMatcher(line,MapCommand.SHOW_MAP))!= null)
                showMap(matcher);
            else if((matcher = MapCommand.getMatcher(line,MapCommand.MOVE_MAP))!=null)
                checkMove(matcher);
            else if((matcher = MapCommand.getMatcher(line,MapCommand.CLEAR))!=null)
                clear(matcher);
            else if((matcher = MapCommand.getMatcher(line,MapCommand.SET_TEXTURE))!= null)
                checkSetTexture(matcher);
            else if((matcher = MapCommand.getMatcher(line,MapCommand.DROP_TREE))!= null)
                checkDrop(matcher,false);
            else if((matcher = MapCommand.getMatcher(line,MapCommand.DROP_ROCK)) != null)
                checkDrop(matcher,true);
            else if(line.equals("cls"))
                clearScreen();
            else if((matcher = MapCommand.getMatcher(line,MapCommand.SET_TEXTURE_ONE_SQUARE))!=null)
                checkSetTextureOneSquare(matcher);
            else if(MapCommand.getMatcher(line,MapCommand.SAVE_MAP)!=null)
                saveMap();
            else if((matcher = MapCommand.getMatcher(line,MapCommand.MAKE_NEW_MAP))!=null)
                makeMap(matcher);
            else
                System.out.println("INVALID COMMAND");
        }

    }

    private void makeMap(Matcher matcher) {
        int size = Integer.parseInt(matcher.group("size"));
        mapController.initializeMap(size,false);
    }

    private void checkDrop(Matcher matcher,boolean mode){// mode==true --> rock
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        MapMessages messages;
        if (mode)
            messages = mapController.dropRock(x,y,type);//type = direction (to avoid hard code)
        else
            messages = mapController.dropTree(x,y,type);
        System.out.println(messages);
    }

    private void checkSetTexture(Matcher matcher){//a rect
        int x1 = Integer.parseInt(matcher.group("x1"));
        int y1 = Integer.parseInt(matcher.group("y1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        int y2 = Integer.parseInt(matcher.group("y2"));
        String type = matcher.group("type");
        MapMessages messages =mapController.setTexture(x1,x2,y1,y2,type);
        System.out.println(messages);
    }

    private void checkSetTextureOneSquare(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        MapMessages messages= mapController.dropRock(x,y,type);
        System.out.println(messages);
    }


    private void clear(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        MapMessages messages = mapController.clear(x,y);
        System.out.println(messages);
    }
    private void showMap(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        System.out.println(mapController.showMap(x,y));
    }

    private void checkMove(Matcher matcher){
        String left = matcher.group("left");
        String mapShow = mapController.moveMap(left);
        clearScreen();
        System.out.println(mapShow);
    }

    private void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void saveMap(){
        mapController.saveMap(user.getUserName());
    }

    public Cell[][] getMyMap() {
        return mapController.getMap();
    }

    public MapController getMapController(){
        return mapController;
    }
}