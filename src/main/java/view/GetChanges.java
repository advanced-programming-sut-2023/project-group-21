package view;

import ServerConnection.DropBuilding;
import ServerConnection.MakeTroop;
import controller.GameController;

import java.io.IOException;

@SuppressWarnings("InfiniteLoopStatement")
public class GetChanges extends Thread {
    private MapViewGui mapViewGui;
    private GameController gameController;
    public boolean canMakeChanges;

    public GetChanges(MapViewGui mapViewGui) {
        this.mapViewGui = mapViewGui;
    }

    @Override
    public void run() {
        System.out.println("Getting Messages");
        try {
            StartingMenu.getDOut().writeObject("change");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                Object object = StartingMenu.getDIn().readObject();
                if (object instanceof String message) {
                    switch (message) {
                        case "yes", "no" -> {
                            canMakeChanges = message.equals("yes");
                        }
                        case "show map" -> mapViewGui.showOriginalMap();
                        case "your turn" -> {
                            System.out.println("My turn now");
                            canMakeChanges = true;
                        }
                        case "game" -> StartingMenu.getDOut().writeObject(mapViewGui.getGameController());
                        case "next turn" -> mapViewGui.nextTurn();
                    }
                } else if (object instanceof DropBuilding building) {
                    mapViewGui.dropBuilding(building);
                } else if (object instanceof MakeTroop makeTroop) {
                    mapViewGui.makeTroop(makeTroop);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
