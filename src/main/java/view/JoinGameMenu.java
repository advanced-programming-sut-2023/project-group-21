package view;

import ServerConnection.GroupGame;
import ServerConnection.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    public void start(Stage stage) throws Exception {
        borderPane = new BorderPane();
        gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(40);
        gridPane.setGridLinesVisible(true);
        gridPane.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        HBox hBox = new HBox();

        Button refresh = new Button("Refresh");
        refresh.relocate(520, 5);
        refresh.setMaxWidth(150);
        refresh.setOnMouseClicked(mouseEvent -> {
            try {
                StartingMenu.getDOut().writeObject("refresh");
                Thread.sleep(50);

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Button exitGroupGame = new Button("Exit Group Game");
        exitGroupGame.setMaxWidth(150);
        exitGroupGame.setOnMouseClicked(mouseEvent -> {
            try {
                StartingMenu.getDOut().writeObject("cancel");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(refresh, exitGroupGame);
        borderPane.setBottom(hBox);
        createGridPane();

        WaitForMap waitForMap = new WaitForMap(this, mainMenu);
        waitForMap.start();

        Scene scene = new Scene(borderPane, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public void createGridPane() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gridPane = new GridPane();
                int count = 0;
                outer:
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 3; j++) {
                        GroupGame group = games.get(count++);
                        if ((!group.isPrivate) || group.getChosenUsers().contains(mainMenu.getUser().getUserName()))
                            gridPane.add(createGameDetails(group), i, j, 1, 1);
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

        VBox names = new VBox();
        names.setSpacing(3);
        names.setAlignment(Pos.CENTER);
        int max = Math.min(5, groupGame.getPlayers().size());
        names.getChildren().add(new Text("Users:"));
        for (int i = 0; i < max; i++) {
            Text text = new Text((i+1) + ". " + groupGame.getPlayers().get(i).getUserName());
            names.getChildren().add(text);
        }
        if (groupGame.getPlayers().size() > 5) names.getChildren().add(new Text("..."));

        Button button = new Button("Select");
        vBox.getChildren().addAll(idText, owner, capacity, names, button);
        button.setOnMouseClicked(mouseEvent -> {
            try {
                String request = "group " + groupGame.getId();
                StartingMenu.getDOut().writeObject(request);
                Thread.sleep(50);
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
