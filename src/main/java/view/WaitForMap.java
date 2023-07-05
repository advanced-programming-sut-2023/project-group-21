package view;

import ServerConnection.GameEssentials;
import ServerConnection.GroupGame;
import ServerConnection.User;
import controller.GameController;
import controller.MapController;
import model.Government;

import java.io.IOException;
import java.util.ArrayList;

public class WaitForMap extends Thread {
    private final MainMenu mainMenu;
    private JoinGameMenu joinGameMenu;

    public WaitForMap(JoinGameMenu joinGameMenu, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.joinGameMenu = joinGameMenu;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        super.run();
        System.out.println("In wait for map");
        while (true) {
            try {
                Object object = StartingMenu.getDIn().readObject();
                if (object instanceof GroupGame groupGame) {
                    System.out.println("Received message to start game.");
                    setupGame(groupGame);
                    return;
                } else if (object instanceof ArrayList<?> games) {
                    System.out.println("Received refreshed.");
                    ArrayList<GroupGame> gameArrayList = (ArrayList<GroupGame>) games;
                    joinGameMenu.games = gameArrayList;
                    joinGameMenu.createGridPane();
                } else if (object instanceof String message) {
                    System.out.println("Received message: " + message);
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized void setupGame(GroupGame groupGame) throws InterruptedException, IOException {
        ArrayList<Government> governments = new ArrayList<>();
        for (int i = 0; i < groupGame.getPlayers().size(); i++)
            governments.add(new Government(groupGame.getPlayers().get(i), groupGame.getMapController().getMyHolds().get(i)));
        GameController gameController = new GameController(governments, groupGame.getMapController().getMap());
        MapViewGui.setStaticGameController(gameController);
        MapViewGui.isInGame = true;
        MapViewGui.user = mainMenu.getUser();
        MapViewGui mapViewGui = new MapViewGui();
//        GetChanges getChanges = new GetChanges(mapViewGui);
//        getChanges.setGameController(gameController);
//        getChanges.start();
//        mapViewGui.setGetChanges(getChanges);
        mapViewGui.setMainMenu(mainMenu);
        mapViewGui.setMapController(groupGame.getMapController());
        mapViewGui.setGameController(gameController);
        for (int i = 0; i < groupGame.getMapController().getMyHolds().size(); i++)
            groupGame.getMapController().getMyHolds().get(i).setExtras(null);
        StartingMenu.getDOut().writeObject("in game");
        mapViewGui.start(StartingMenu.mainStage);
    }
}
