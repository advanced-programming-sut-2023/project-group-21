package view;

import ServerConnection.GameEssentials;
import ServerConnection.GroupGame;
import controller.FileController;
import controller.GameController;
import controller.MapController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.shape.Line;
import ServerConnection.Cell;
import javafx.util.Duration;
import model.Government;
import ServerConnection.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class MainMenu extends Application {
    private final static String pathCssFile = MainMenu.class.getResource("/CSS/Texture.css").toString();
//            "file:" + (new File("").getAbsolutePath()) +
//            "/src/main/resources/CSS/Texture.css";
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

    private void initController() {
        if (mapController != null) {
            if (user != null)
                mapController.setUser(user);
            mapController.loadMapNormal();
            return;
        }
        if (staticMapController != null)
            mapController = staticMapController;
        if (user != null)
            mapController.setUser(user);
        mapController.loadMapNormal();
    }


    public void setLoggingMenu(LoggingMenu loggingMenu) {
        this.loggingMenu = loggingMenu;
    }

    @Override
    public void start(Stage stage) {
        if (staticMapController == null) {
            mapController = new MapController();
            mapController.setUser(user);
            staticMapController = mapController;
        } else {
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
        Button profileButton = new Button("Profile");
        Button gameButton = new Button("Game");
        Button quitButton = new Button("Quit");


        gameButton.relocate(760, 60);
        profileButton.relocate(760, 120);
        mapButton.relocate(760, 180);
        quitButton.relocate(760, 240);

        //behaviour
        quitButton.setOnMouseClicked(mouseEvent -> {
            initController();
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                //repair
                //the government
//                Government government=new Government(user,new Cell(20,20));
//                ShoppingMenu shoppingMenu=new ShoppingMenu();
//                shoppingMenu.setGovernment(government);
//                try {
//                    shoppingMenu.start(StartingMenu.mainStage);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
                try {
                    StartingMenu.getDOut().writeObject("logout");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FileController.finish();
                Platform.exit();
            }
        });

        mapButton.setOnMouseClicked(mouseEvent -> {
            initController();
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
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

        profileButton.setOnMouseClicked(mouseEvent -> {
            Profile profile=new Profile();
            profile.setUser(user);
            try {
                profile.start(StartingMenu.mainStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        mainPane.getChildren().addAll(gameButton, profileButton, mapButton, quitButton);
    }

    private void addBorders() {
        Line[] lines = new Line[6];
        lines[0] = new Line(1070, 10, 1070, 150);
        lines[1] = new Line(380, 10, 1070, 10);
        lines[2] = new Line(1065, 15, 1065, 150);
        lines[3] = new Line(380, 15, 1065, 15);
        lines[4] = new Line(380, 10, 380, 15);
        lines[5] = new Line(1065, 150, 1070, 150);
        for (Line line : lines) {
            lineColor(line);
            mainPane.getChildren().add(line);
        }

        Line[] lines2 = new Line[6];
        lines2[0] = new Line(10, 150, 10, 710);
        lines2[1] = new Line(15, 150, 15, 705);
        lines2[2] = new Line(10, 150, 15, 150);
        lines2[3] = new Line(10, 710, 1070, 710);
        lines2[4] = new Line(15, 705, 1070, 705);
        lines2[5] = new Line(1070, 705, 1070, 710);
        for (Line line : lines2) {
            lineColor(line);
            mainPane.getChildren().add(line);
        }
    }

    private void lineColor(Line line) {
        line.setFill(null);
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(1);
    }

    private void startGame() {
        initController();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Start Game");
        stage.setMaxWidth(400);
        stage.setMinWidth(400);
        stage.setMaxHeight(400);
        stage.setMinHeight(400);
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(pane, 500, 600);
        scene.getStylesheets().add(pathCssFile);
        stage.setScene(scene);
        TextField numberOfPlayers = new TextField();
        numberOfPlayers.setPromptText("Number of Players");
        numberOfPlayers.setMaxWidth(40);
        numberOfPlayers.relocate(100, 30);

        Button joinGame = new Button("Join a Game");
        joinGame.relocate(200, 120);
        joinGame.setPrefWidth(150);

        Button createGame = new Button("New Game");
        createGame.setPrefWidth(150);
        createGame.relocate(100, 120);

        Button privateGame = new Button("Private");
        privateGame.setPrefWidth(150);
        privateGame.relocate(300, 120);

        Button watchGame = new Button("Watch");
        watchGame.setPrefWidth(150);
        watchGame.relocate(200, 150);

        stage.show();
        pane.setStyle("-fx-max-height: 200;" + "-fx-min-height: 200;");
        pane.getChildren().addAll(createGame, joinGame, numberOfPlayers, privateGame, watchGame);

        createGame.setOnMouseClicked(mouseEvent -> {
            ArrayList<Cell> myHolds = mapController.getMyHolds();
            System.out.println(myHolds);
            if (myHolds.size() < 2) {
                Stage stage1 = new Stage();
                Label label = new Label("too few hold!");
                Scene scene1 = new Scene(label);
                scene1.getStylesheets().add(pathCssFile);
                stage1.setScene(scene1);
                stage1.show();
            } else {
                String count = numberOfPlayers.getText();
                if (count.trim().isEmpty() || !count.matches("\\d+")) {
                    //TODO: add proper message
                    System.out.println("not a number");
                    return;
                }
                int playerCount = Integer.parseInt(count);
                try {
                    GroupGame groupGame = new GroupGame(user, playerCount, mapController);
                    StartingMenu.getDOut().writeObject(groupGame);
                    Thread.sleep(50);
                    Object object = StartingMenu.getDIn().readObject();
                    if (object instanceof Integer number) {
                        groupGame.setId(number);
                        pane.getChildren().remove(0, pane.getChildren().size());
                        Text text = new Text("Waiting for other users\nto join...");
                        text.relocate(100, 60);
                        text.setTextAlignment(TextAlignment.CENTER);
                        pane.getChildren().add(text);
                        stage.close();
                        WaitScene();
                    } else if (object instanceof String message && message.equals("duplicate")) {
                        Text text = new Text("You have already created\na game.");
                        text.relocate(100, 60);
                        text.setTextAlignment(TextAlignment.CENTER);
                        pane.getChildren().add(text);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        joinGame.setOnMouseClicked(mouseEvent -> {
            try {
                StartingMenu.getDOut().writeObject("join");
                Thread.sleep(50);
                Object object = StartingMenu.getDIn().readObject();
                if (object == null || (object instanceof ArrayList<?> groupGames && groupGames.isEmpty()))
                    System.out.println("empty");
                else {
                    JoinGameMenu menu = new JoinGameMenu();
                    menu.setGames((ArrayList<GroupGame>) object);
                    menu.setMainMenu(this);
                    stage.close();
                    menu.start(StartingMenu.mainStage);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        privateGame.setOnMouseClicked(mouseEvent -> {
            try {
                String count = numberOfPlayers.getText();
                if (count.trim().isEmpty() || !count.matches("\\d+")) {
                    //TODO: add proper message
                    System.out.println("not a number");
                    return;
                }
                int playerCount = Integer.parseInt(count);
                pane.getChildren().remove(0, pane.getChildren().size());
                ArrayList<TextField> playersUsernames = new ArrayList<>();
                for (int i = 1; i < playerCount; i++) {
                    TextField textField = new TextField();
                    textField.setMaxWidth(50);
                    textField.setPromptText("User no." + (i+1));
                    playersUsernames.add(textField);
                }
                VBox vBox = new VBox();
                vBox.setAlignment(Pos.CENTER);
                vBox.setSpacing(15);
                vBox.getChildren().addAll(playersUsernames);

                Button startGame = new Button("Start Game");
                startGame.setPrefWidth(100);
                startGame.setTextFill(Color.DARKORANGE);
                startGame.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                startGame.setOnMouseClicked(mouseEvent1 -> {
                    for (TextField textField: playersUsernames) {
                        if (textField.getText().trim().isEmpty()) {
                            Text text = new Text("You Have Not Specified\nAll Users.");
                            text.setFill(Color.GREEN);
                            text.setStroke(Color.BLACK);
                            text.relocate(120, 500);
                            pane.getChildren().add(text);
                            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent ->
                                    pane.getChildren().remove(text)));
                            timeline.play();
                            return;
                        }
                    }
                    try {
                        ArrayList<String> usernames = new ArrayList<>();
                        usernames.add(user.getUserName());
                        for (TextField textField: playersUsernames) usernames.add(textField.getText());
                        System.out.println(usernames);
                        GroupGame game = new GroupGame(user, playerCount, mapController, usernames);
                        StartingMenu.getDOut().writeObject(game);
                        Thread.sleep(50);
                        Object object = StartingMenu.getDIn().readObject();
                        if (object instanceof Integer number) {
                            game.setId(number);
                            pane.getChildren().remove(0, pane.getChildren().size());
                            Text text = new Text("Waiting for other users\nto join...");
                            text.relocate(100, 60);
                            text.setTextAlignment(TextAlignment.CENTER);
                            pane.getChildren().add(text);
                            stage.close();
                            WaitScene();
                        } else if (object instanceof String message && message.equals("duplicate")) {
                            Text text = new Text("You have already created\na game.");
                            text.relocate(100, 60);
                            text.setTextAlignment(TextAlignment.CENTER);
                            pane.getChildren().add(text);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

                vBox.getChildren().add(startGame);
                vBox.relocate(90, 10);
                pane.getChildren().add(vBox);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        watchGame.setOnMouseClicked(mouseEvent -> {
            try {
                StartingMenu.getDOut().writeObject("watch");
                Thread.sleep(50);
                Object obj = StartingMenu.getDIn().readObject();
                if (obj == null) {
                    System.out.println("No Game Available.");
                    return;
                }
                GroupGame game = (GroupGame) obj;
                ArrayList<Government> governments = new ArrayList<>();
                for (int i = 0; i < game.getPlayers().size(); i++)
                    governments.add(new Government(game.getPlayers().get(i), game.getMapController().getMyHolds().get(i)));
                GameController gameController = new GameController(governments, game.getMapController().getMap());
                MapViewGui.setStaticGameController(gameController);
                MapViewGui.isInGame = true;
                MapViewGui.watch = true;
                MapViewGui mapViewGui = new MapViewGui();
                mapViewGui.setMainMenu(this);
                mapViewGui.setMapController(game.getMapController());
                mapViewGui.setGameController(gameController);
                mapViewGui.start(StartingMenu.mainStage);
                stage.close();
            } catch (IOException | InterruptedException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

//        ArrayList<Government> governments = new ArrayList<>();
//        governments.add(new Government(user, myHolds.get(0)));
//        TextField textField = new TextField();
//        textField.relocate(50, 50);
//        textField.setMinWidth(100);
//        textField.setMinWidth(100);
//        Label stateLabel = new Label();
//        stateLabel.setMaxWidth(60);
//        stateLabel.setMinWidth(60);
//        stateLabel.relocate(70, 100);
//        String usernamePlay = "";
//        Button addPlayer = new Button("add player");
//        addPlayer.relocate(150, 150);
//        Button startGame = new Button("start");
//        startGame.relocate(50, 150);
//        addPlayer.setOnMouseClicked(mouseEvent -> {
//            if (governments.size() < myHolds.size()) {
//                if (FileController.checkExistenceOfUserOrEmail(textField.getText(), true)) {
//                    User user1 = FileController.getUserByUsername(textField.getText());
//                    textField.setText("");
//                    stateLabel.setText("success!");
//                    governments.add(new Government(user1, myHolds.get(governments.size())));
//                } else
//                    stateLabel.setText("user does not exist");
//            } else
//                stateLabel.setText("too many player!");
//        });
//        startGame.setOnMouseClicked(mouseEvent -> {
//            if (governments.size() >= 2) {
//                for (int i = 0; i<myHolds.size() ; i++)
//                    myHolds.get(i).setExtras(null);
//                GameController gameController = new GameController(governments,mapController.getMap());
//                MapViewGui.setStaticGameController(gameController);
//                MapViewGui.isInGame = true;
//                MapViewGui mapViewGui = new MapViewGui();
//                mapViewGui.setMainMenu(this);
//                mapViewGui.setMapController(mapController);
//                mapViewGui.setGameController(gameController);
//                mapViewGui.start(StartingMenu.mainStage);//start the game ctl
//                stage.close();
//
//            }
//        });
    }

    private void WaitScene() throws Exception {
        WaitForJoinMenu menu = new WaitForJoinMenu();
        menu.setUser(user);
        WaitForJoin waitForJoin = new WaitForJoin(menu, mapController, this);
        waitForJoin.start();
        menu.start(StartingMenu.mainStage);
    }

    public User getUser() {
        return user;
    }
}
