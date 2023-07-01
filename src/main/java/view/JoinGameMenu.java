package view;

import ServerConnection.GameEssentials;
import ServerConnection.GroupGame;
import controller.GameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class JoinGameMenu extends Application {
    public ArrayList<GroupGame> games;
    private GridPane gridPane;
    private MainMenu mainMenu;
    private BorderPane borderPane;
    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {
        borderPane = new BorderPane();
        gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(40);
        gridPane.setGridLinesVisible(true);
        gridPane.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Button button = new Button("Refresh");
        button.relocate(520, 5);
        borderPane.setBottom(button);
        button.setOnMouseClicked(mouseEvent -> {
            try {
                StartingMenu.getDOut().writeObject("join");
                Thread.sleep(50);

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        createGridPane();
        Scene scene = new Scene(borderPane, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public void createGridPane() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Fuck");
                gridPane = new GridPane();
                System.out.println("Grid size: " + gridPane.getChildren().size());
                System.out.println("Game size: " + games.get(0).getPlayers().size());
                int count = 0;
                outer:
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 3; j++) {
                        gridPane.add(createGameDetails(games.get(count++)), i, j, 1, 1);
                        if (count == games.size()) break outer;
                    }
                }
                borderPane.setCenter(gridPane);
            }
        });
    }

    private VBox createGameDetails(GroupGame groupGame) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Text idText = new Text("ID: " + groupGame.getId());
        Text owner = new Text("Owner: " + groupGame.getOwner().getUserName());
        Text capacity = new Text("Capacity: " + groupGame.getPlayers().size() + " / " + groupGame.getSize());
        Button button = new Button("Select");
        vBox.getChildren().addAll(idText, owner, capacity, button);
        button.setOnMouseClicked(mouseEvent -> {
            try {
                String request = "group " + groupGame.getId();
                StartingMenu.getDOut().writeObject(request);
                Thread.sleep(50);
                WaitForMap waitForMap = new WaitForMap(this, mainMenu);
                waitForMap.start();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return vBox;
    }

    public void setGames(ArrayList<GroupGame> games) {
        this.games = games;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }
}
