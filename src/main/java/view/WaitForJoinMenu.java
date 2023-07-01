package view;

import ServerConnection.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class WaitForJoinMenu extends Application {
    private BorderPane borderPane;
    private User user;
    @FXML
    private Button exit;
    @Override
    public void start(Stage stage) throws Exception {
        borderPane = FXMLLoader.load(
                Objects.requireNonNull(WaitForJoinMenu.class.getResource("/FXML/WaitForJoin.fxml")));
        Background background = new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY));
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);

        Button forceStart = new Button("Force Start");
        forceStart.setTextAlignment(TextAlignment.CENTER);
        forceStart.setAlignment(Pos.CENTER);
        forceStart.setMaxWidth(200);
        forceStart.setTextFill(Color.AQUA);
        forceStart.setBackground(background);
        forceStart.setOnMouseClicked(mouseEvent -> {
            try {
                forceStart();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button exit = new Button("Exit Group");
        exit.setTextAlignment(TextAlignment.CENTER);
        exit.setAlignment(Pos.CENTER);
        exit.setMaxWidth(200);
        exit.setTextFill(Color.AQUA);
        exit.setBackground(background);
        exit.setOnMouseClicked(mouseEvent -> {
            try {
                exitGroup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        hBox.getChildren().addAll(forceStart, exit);
        borderPane.setCenter(hBox);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    public void forceStart() throws IOException {
        StartingMenu.getDOut().writeObject("force start");
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void notEnoughUser() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                HBox hBox = new HBox();
                Text text = new Text("No user has joined your game yet.");
                text.setTextAlignment(TextAlignment.CENTER);
                text.setFill(Color.RED);
                hBox.getChildren().add(text);
                hBox.setAlignment(Pos.CENTER);
                borderPane.setBottom(hBox);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> borderPane.getChildren().remove(hBox)));
                timeline.play();
            }
        });
    }

    public void exitGroup() throws IOException {
        MainMenu menu = new MainMenu();
        menu.setUser(user);
        StartingMenu.getDOut().writeObject("exit group");
        menu.start(StartingMenu.mainStage);
    }
}
