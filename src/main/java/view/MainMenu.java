package view;

import controller.FileController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.shape.Line;
import model.User;

import java.net.URL;

public class MainMenu extends Application {
    public void setUser(User user) {
        this.user = user;
    }

    private User user;
    private Pane mainPane;
    private LoggingMenu loggingMenu;

    public void setLoggingMenu(LoggingMenu loggingMenu) {
        this.loggingMenu = loggingMenu;
    }

    @Override
    public void start(Stage stage) {
        URL url = StartMenu.class.getResource("/FXML/MainMenu.fxml");
        try {
            mainPane = FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addNodes();
        addBorders();


        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
    }

    private void addNodes() {
        Button mapButton = new Button("Map");
        Button profileButton=new Button("Profile");
        Button gameButton=new Button("Game");
        Button quitButton=new Button("Quit");

        gameButton.relocate(760,60);
        profileButton.relocate(760,120);
        mapButton.relocate(760,180);
        quitButton.relocate(760,240);

        //behaviour
        quitButton.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()== MouseButton.PRIMARY) {
                FileController.finish();
                Platform.exit();
            }
        });

        mapButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                MapViewGui mapViewGui = new MapViewGui();
                mapViewGui.setMainMenu(this);
                mapViewGui.setUser(user);
            }
        });

        mainPane.getChildren().addAll(gameButton,profileButton,mapButton,quitButton);
    }

    private void addBorders() {
        Line[] lines=new Line[6];
        lines[0]=new Line(1070,10,1070,150);
        lines[1]=new Line(380,10,1070,10);
        lines[2]=new Line(1065,15,1065,150);
        lines[3]=new Line(380,15,1065,15);
        lines[4]=new Line(380,10,380,15);
        lines[5]=new Line(1065,150,1070,150);
        for (Line line : lines) {
            lineColor(line);
            mainPane.getChildren().add(line);
        }

        Line[] lines2=new Line[6];
        lines2[0]=new Line(10,150,10,710);
        lines2[1]=new Line(15,150,15,705);
        lines2[2]=new Line(10,150,15,150);
        lines2[3]=new Line(10,710,1070,710);
        lines2[4]=new Line(15,705,1070,705);
        lines2[5]=new Line(1070,705,1070,710);
        for (Line line : lines2) {
            lineColor(line);
            mainPane.getChildren().add(line);
        }
    }

    private void lineColor(Line line){
        line.setFill(null);
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(1);
    }

}
