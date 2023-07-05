package view;

import ServerConnection.GameEssentials;
import ServerConnection.GroupGame;
import controller.GameController;
import controller.MapController;
import model.Government;

import java.io.IOException;
import java.util.ArrayList;

public class WaitForJoin extends Thread {
    private final MapController mapController;
    private final MainMenu mainMenu;
    private final WaitForJoinMenu waitForJoinMenu;

    public WaitForJoin(WaitForJoinMenu waitForJoinMenu, MapController mapController, MainMenu mainMenu) {
        this.waitForJoinMenu = waitForJoinMenu;
        this.mapController = mapController;
        this.mainMenu = mainMenu;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("In Wait");
        while (true) {
            try {
                Object object = StartingMenu.getDIn().readObject();
                if (object instanceof GroupGame groupGame) {
                    System.out.println("Received permission to start game.");
                    setupGame(groupGame);
                    return;
                } else if (object instanceof String message)
                    if (message.equals("no")) waitForJoinMenu.notEnoughUser();
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
