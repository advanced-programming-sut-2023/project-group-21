package view;

import controller.FileController;
import controller.MapController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import javafx.scene.shape.Line;
import model.Cell;
import model.Government;
import model.User;

import java.net.URL;
import java.util.ArrayList;

public class MainMenu extends Application {
    private final static String pathCssFile = "file:" + (new File("").getAbsolutePath()) +
            "/src/main/resources/CSS/Texture.css";
    private Stage mainStage;
    public void setUser(User user) {
        this.user = user;
    }
    private MapController mapController;
    public static MapController staticMapController;

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
        staticMapController = mapController;
    }

    private User user;
    private Pane mainPane;
    private LoggingMenu loggingMenu;

    private void initController(){
        if (mapController != null)
            return;
        if (staticMapController != null)
            mapController = staticMapController;
        if (FileController.checkExistenceOfMap(user.getUserName()))
            mapController.initializeMap(FileController.loadMap(user.getUserName()));
        else
            mapController.initializeMap(400,true);
    }


    public void setLoggingMenu(LoggingMenu loggingMenu) {
        this.loggingMenu = loggingMenu;
    }

    @Override
    public void start(Stage stage) {
        if (staticMapController == null){
            mapController = new MapController();
            mapController.setUser(user);
            staticMapController = mapController;
        }else {
            mapController = staticMapController;
        }
        if (stage != null)
            mainStage = stage;
        else
            mainStage = StartingMenu.mainStage;
        URL url = StartMenu.class.getResource("/FXML/MainMenu.fxml");
        try {
            mainPane = FXMLLoader.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addNodes();
        addBorders();


        Scene scene = new Scene(mainPane);
        StartingMenu.mainStage.setScene(scene);
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
            initController();
            if(mouseEvent.getButton()== MouseButton.PRIMARY) {
                FileController.finish();
                Platform.exit();
            }
        });

        mapButton.setOnMouseClicked(mouseEvent -> {
            initController();
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                MapViewGui mapViewGui = new MapViewGui();
                mapViewGui.setMapController(mapController);
                mapViewGui.start(mainStage);
                mapViewGui.setMainMenu(this);
            }
        });

        gameButton.setOnMouseClicked(mouseEvent -> {
            initController();
            startGame();
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

    private void startGame(){
        initController();
        ArrayList<Cell> myHolds = mapController.getMyHolds();
        if (myHolds.size()<2){
            return;
        }
        ArrayList<Government> governments = new ArrayList<>();
        governments.add(new Government(user,myHolds.get(0)));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("start game");
        stage.setMaxWidth(200);stage.setMinWidth(200);stage.setMaxHeight(200);stage.setMinHeight(200);//set size
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(pathCssFile);
        stage.setScene(scene);
        TextField textField = new TextField();textField.relocate(150,50);
        textField.setMinWidth(100);textField.setMinWidth(100);
        Label stateLabel = new Label();
        stateLabel.setMaxWidth(60);stateLabel.setMinWidth(60);
        stateLabel.relocate(70,100);
        String usernamePlay = "";
        Button addPlayer = new Button("add player");
        addPlayer.relocate(150,150);
        Button startGame = new Button("start");
        startGame.relocate(50,150);
        addPlayer.setOnMouseClicked(mouseEvent -> {
            if (governments.size() < myHolds.size()){
                if(FileController.checkExistenceOfUserOrEmail(textField.getText(),true)){
                    User user1 = FileController.getUserByUsername(textField.getText());
                    textField.setText("");
                    governments.add(new Government(user1,myHolds.get(governments.size())));
                }
            }
        });
        startGame.setOnMouseClicked(mouseEvent -> {
            if (governments.size()>=2){
                MapViewGui mapViewGui = new MapViewGui();
                mapViewGui.setMainMenu(this);
                mapViewGui.setMapController(mapController);
                mapViewGui.setGovernments(governments);
                stage.close();
                mapViewGui.start(mainStage);
            }
        });
    }

}
