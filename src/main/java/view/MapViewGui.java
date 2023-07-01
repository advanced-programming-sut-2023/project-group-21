package view;

import ServerConnection.DropBuilding;
import ServerConnection.MakeTroop;
import controller.GameController;
import controller.MapController;
import controller.VboxCreator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import ServerConnection.Cell;
import model.Government;
import model.building.Enums.BuildingsDetails;
import model.generalenums.Extras;
import model.generalenums.GroundTexture;
import model.human.Enums.WorkerDetails;
import model.human.Worker;
import model.machine.MachineDetails;
import view.message.GameMessage;
import view.message.MapMessages;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;


public class MapViewGui extends Application implements Initializable, Runnable {
    private final static String pathCssFile = "file:" + (new File("").getAbsolutePath()) +
            "/src/main/resources/CSS/Texture.css";
    private final static String MY_PATH = new File("").getAbsolutePath();
    private String[] textureItem;
    private final Label errorLabel = new Label("");
    private double startDragX = 0, startDragY = 0;
    private int CELL_SIZE = 75;
    private final int MINI_MAP_SIZE = 10;
    @FXML
    private Pane cellPane, miniMap;
    @FXML
    private AnchorPane mainPane;
    private AnchorPane anchorPane;
    private ArrayList<Worker> guiWorker;
    private Cell[][] showingMap;
    private int destinationX = 5, destinationY = 5;
    private int currentX = 5, currentY = 5;
    public MapController mapController;
    public static MapController mapControllerStatic;
    private boolean isStartDrag = false;
    private static MainMenu staticMainMenu;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox objectBox;
    private final double X_CELL_PANE = 256.0, Y_CELL_PANE = 28.0;
    private double startMoveX = 0, startMoveY = 0;
    private int selectedX1 = -1, selectedX2 = -1, selectedY1 = -1, selectedY2 = -1;//-1 means there is no selected cell!
    private boolean isDraggedExtra = false;
    private Extras selectedExtra;
    private long currentTimeZoom = System.currentTimeMillis();//set current time for zoom
    private double XFullDrag = 0, YFullDrag = 0;
    private Stage mainStage;
    private MainMenu mainMenu;
    private GetChanges getChanges;


    public static boolean isInGame = false;
    private GameController gameController;
    private static GameController staticGameController;
    private BuildingsDetails selectedBuildingDetails;
    private MachineDetails selectedMachineDetails;
    private WorkerDetails selectedWorkerDetails;
    private final SplitPane details = new SplitPane(), resourcesDetails = new SplitPane();


    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        staticGameController = gameController;
    }

    public static void setStaticGameController(GameController gameController) {
        MapViewGui.staticGameController = gameController;
    }


    public void setMainMenu(MainMenu mainMenu) {
        staticMainMenu = mainMenu;
        this.mainMenu = mainMenu;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        if (mapControllerStatic == null) {
            mapController = new MapController();
            mapControllerStatic = mapController;
        } else
            mapController = mapControllerStatic;
        if (gameController == null)
            gameController = staticGameController;
        this.mainStage = stage;
        try {
            anchorPane = FXMLLoader.load(MapController.class.getResource("/FXML/Map.fxml"));
            BackgroundImage myBI = new BackgroundImage(
                    new Image(Objects.requireNonNull(MainMenu.class.getResource("/images/Gamebg.png")).toExternalForm(),
                            1080, 720, false, false),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            anchorPane.setBackground(new Background(myBI));
            Scene mapScene = new Scene(anchorPane);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stage.setScene(mapScene);
                    stage.show();
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    private void startShop(){
        if (!isInGame)
            return;
        ShoppingMenu shoppingMenu=new ShoppingMenu();
        shoppingMenu.setGovernment(gameController.getCurrentGovernment());
        shoppingMenu.setGameController(gameController);
        try {
            Stage shoppingStage = new Stage();
            shoppingStage.setOnCloseRequest(windowEvent -> {
                updateDetailsBox();
                updateResourcesBox();
            });
            shoppingMenu.start(shoppingStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showMap(Cell[][] myMap) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showingMap = myMap;
                if (cellPane.getChildren().size() > 1)
                    cellPane.getChildren().remove(0, cellPane.getChildren().size());
                for (int i1 = 0; i1 < myMap.length && i1 < (600 / CELL_SIZE); i1++)
                    for (int i2 = 0; i2 < myMap.length && i2 < (600 / CELL_SIZE); i2++) {
                        Label label = myMap[i1][i2].toLabel(CELL_SIZE * i1, CELL_SIZE * i2, CELL_SIZE);
                        int finalI = i1;
                        if ((currentX + i1 - 1) >= selectedX1 && (currentX + i1 - 1) <= selectedX2 &&
                                (currentY + i2 - 1) >= selectedY1 && (currentY + i2 - 1) <= selectedY2) {
                            label.setOpacity(0.4);
                        }
                        int finalI1 = i2;
                        label.setOnMouseClicked(mouseEvent -> {
                            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                                System.out.println("click on sec");
                                isDraggedExtra = false;
                                if (!isInGame) {
                                    if ((currentX + finalI - 1) <= selectedX2 && (currentX + finalI - 1) >= selectedX1 &&
                                            (currentY + finalI1 - 1) <= selectedY2 && (currentY + finalI1 - 1) >= selectedY1)
                                        setTexture(selectedX1, selectedY1, selectedX2, selectedY2);
                                    else
                                        setTexture(currentX + finalI - 1, currentY + finalI1 - 1,
                                                currentX + finalI - 1, currentY + finalI1 - 1);
                                } else {
                                    showMoveStage(finalI + currentX - 1, finalI1 + currentY - 1);
                                }
                            }
                        });
                        label.setTooltip(new Tooltip(mapController.showDetails(currentX + i1, currentY + i2)));

                        cellPane.getChildren().add(label);
                    }
                if (600 % CELL_SIZE != 0) {
                    int thing = 600 / CELL_SIZE;
                    for (int i = 0; i < thing && i < myMap.length; i++) {
                        Label label = myMap[thing][i].toLabel(thing * CELL_SIZE, i * CELL_SIZE,
                                (600 - CELL_SIZE * (thing)), CELL_SIZE);
                        cellPane.getChildren().add(label);
                    }
                    for (int i = 0; i < (600 / CELL_SIZE) && i < myMap.length; i++) {
                        Label label = myMap[i][thing].toLabel(i * CELL_SIZE, thing * CELL_SIZE, CELL_SIZE,
                                600 - CELL_SIZE * thing);
                        cellPane.getChildren().add(label);
                    }
                    Label label = myMap[thing][thing].toLabel(CELL_SIZE * thing,
                            CELL_SIZE * thing, 600 - CELL_SIZE * thing, 600 - CELL_SIZE * thing);
                    cellPane.getChildren().add(label);
                }
            }
        });
    }


    public void initGame() {
        initializeDetailsBox();
        initializeResourcesBox();
        createCategoryBox();
        objectBox.getChildren().remove(0, objectBox.getChildren().size());
        ImageView imageView;
        Image image;
        Label label;
        for (Map.Entry<String, BuildingsDetails> entry : VboxCreator.CASTLE_BUILDINGS.entrySet()) {
            image = new Image(entry.getKey());
            imageView = new ImageView(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(70);
            label = new Label(null, imageView);
            label.setTooltip(new Tooltip(entry.getValue().getName()));
            label.setStyle("-fx-border-color: black;");
            label.setOnMousePressed(mouseEvent -> selectedBuildingDetails = entry.getValue());
            objectBox.getChildren().add(label);
        }
    }

    private void createCategoryBox() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setMaxWidth(200);
        vBox.setTranslateX(7);
        vBox.setTranslateY(580);
        Button foodProcess = new Button("Food Process");
        foodProcess.setMaxWidth(100);
        foodProcess.setMinWidth(100);
        foodProcess.setFont(Font.font(10));
        foodProcess.setOnMouseClicked(mouseEvent -> updateObjectBox(VboxCreator.FOOD_PROCESSING_BUILDINGS));

        HBox hBox1 = new HBox();
        Button castle = new Button("Castle");
        castle.setFont(Font.font(10));
        castle.setMaxWidth(100);
        castle.setMinWidth(100);
        castle.setOnMouseClicked(mouseEvent -> updateObjectBox(VboxCreator.CASTLE_BUILDINGS));
        Button farm = new Button("Farm");
        farm.setMaxWidth(100);
        farm.setMinWidth(100);
        farm.setFont(Font.font(10));
        farm.setOnMouseClicked(mouseEvent -> updateObjectBox(VboxCreator.FARM_BUILDINGS));
        Button industry = new Button("Industry");
        industry.setFont(Font.font(10));
        industry.setMaxWidth(100);
        industry.setMinWidth(100);
        industry.setOnMouseClicked(mouseEvent -> updateObjectBox(VboxCreator.INDUSTRY_BUILDINGS));
        hBox1.getChildren().addAll(castle, farm, industry);

        HBox hBox2 = new HBox();
        Button machine = new Button("Machine");
        machine.setMaxWidth(100);
        machine.setMinWidth(100);
        machine.setFont(Font.font(10));
        machine.setOnMouseClicked(mouseEvent -> updateObjectBox(null));
        Button town = new Button("Town");
        town.setMaxWidth(100);
        town.setMinWidth(100);
        town.setFont(Font.font(10));
        town.setOnMouseClicked(mouseEvent -> updateObjectBox(VboxCreator.TOWN_BUILDING));
        Button weapon = new Button("Weapon");
        weapon.setMaxWidth(100);
        weapon.setMinWidth(100);
        weapon.setFont(Font.font(10));
        weapon.setOnMouseClicked(mouseEvent -> updateObjectBox(VboxCreator.WEAPONS_BUILDINGS));
        hBox2.getChildren().addAll(machine, town, weapon);

        vBox.getChildren().addAll(foodProcess, hBox1, hBox2);
        mainPane.getChildren().add(vBox);
    }

    private void initializeResourcesBox() {
        mainPane.getChildren().add(resourcesDetails);
        resourcesDetails.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        resourcesDetails.setOpacity(0.7);
        resourcesDetails.setTranslateX(50);
        resourcesDetails.setTranslateY(150);
        resourcesDetails.setPrefWidth(120);
        resourcesDetails.setOrientation(Orientation.VERTICAL);
        resourcesDetails.setDividerPositions(0.2);
        Text text = new Text("Resources");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        resourcesDetails.getItems().addAll(text, gameController.getResourceList());
    }

    private void initializeDetailsBox() {
        mainPane.getChildren().add(details);
        details.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        details.setOpacity(0.7);
        details.setTranslateX(900);
        details.setTranslateY(10);
        details.setOrientation(Orientation.VERTICAL);
        details.setDividerPositions(0.2);
        VBox properties = new VBox();
        properties.getChildren().addAll(new Text("\nPopularity: " + gameController.getPopularity()),
                new Text("Rate: " + gameController.getPopularityRate()),
                new Text("Gold: " + gameController.getGold()));
        properties.setAlignment(Pos.CENTER);
        properties.setSpacing(20);
        properties.setMaxWidth(500);
        details.setPrefWidth(120);
        Text text = new Text("Details");
        text.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
        details.getItems().addAll(text, properties);
    }

    private void updateResourcesBox() {
        resourcesDetails.getItems().remove(1);
        resourcesDetails.getItems().add(gameController.getResourceList());
    }

    private void copyBuilding() {
        if (selectedX2 == selectedX1 && selectedY1 == selectedY2) {
            if (mapController.getCell(selectedX1, selectedY1).getBuilding() == null)
                alert(GameMessage.NOTHING_TO_COPY);
            else {
                gameController.setCopyBuildingDetail(mapController.getCell(selectedX1, selectedY1).
                        getBuilding().getBuildingsDetails());
                alert(GameMessage.SUCCESS_COPY);//copied successfully
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putImage(new Image(MY_PATH + mapController.getCell(selectedX1, selectedY1).getBuilding().
                        getBuildingsDetails().getImagePath()));
                clipboard.setContent(content);
            }
        } else
            alert(GameMessage.UNABLE_TO_COPY);
    }

    private void updateDetailsBox() {
        VBox properties = (VBox) details.getItems().get(1);
        properties.getChildren().remove(0, properties.getChildren().size());
        properties.getChildren().addAll(new Text("\nPopularity: " + gameController.getPopularity()),
                new Text("Rate: " + gameController.getPopularityRate()),
                new Text("Gold: " + gameController.getGold()));
    }

    private void updateObjectBox(Map<String, BuildingsDetails> map) {
        if (map == null) {
            objectBox.getChildren().remove(0, objectBox.getChildren().size());
            ImageView imageView;
            Image image;
            Label label;
            for (Map.Entry<String, MachineDetails> entry : VboxCreator.MACHINES.entrySet()) {
                image = new Image(entry.getKey());
                imageView = new ImageView(image);
                imageView.setFitWidth(90);
                imageView.setFitHeight(70);
                label = new Label(null, imageView);
                label.setTooltip(new Tooltip(entry.getValue().getName()));
                label.setStyle("-fx-border-color: black;");
                label.setOnMousePressed(mouseEvent -> {
                    selectedMachineDetails = entry.getValue();
                    selectedExtra = null;
                    selectedWorkerDetails = null;
                    selectedBuildingDetails = null;
                });
                objectBox.getChildren().add(label);
            }
        } else {
            objectBox.getChildren().remove(0, objectBox.getChildren().size());
            ImageView imageView;
            Image image;
            Label label;
            for (Map.Entry<String, BuildingsDetails> entry : map.entrySet()) {
                image = new Image(entry.getKey());
                imageView = new ImageView(image);
                imageView.setFitWidth(90);
                imageView.setFitHeight(70);
                label = new Label(null, imageView);
                label.setStyle("-fx-border-color: black;");
                label.setTooltip(new Tooltip(entry.getValue().getName()));
                label.setOnMousePressed(mouseEvent -> {
                    selectedBuildingDetails = entry.getValue();
                    selectedExtra = null;
                    selectedWorkerDetails = null;
                    selectedMachineDetails = null;
                });
                objectBox.getChildren().add(label);
            }
        }
    }

    private void updateObjectBoxTroops(Map<String, WorkerDetails> map) {
        if (map == null) {
            objectBox.getChildren().remove(0, objectBox.getChildren().size());
            ImageView imageView;
            Image image;
            Label label;
            for (Map.Entry<String, MachineDetails> entry : VboxCreator.MACHINES.entrySet()) {
                image = new Image(entry.getKey());
                imageView = new ImageView(image);
                imageView.setFitWidth(90);
                imageView.setFitHeight(70);
                label = new Label(null, imageView);
                label.setStyle("-fx-border-color: black;");
                label.setTooltip(new Tooltip(entry.getValue().getName()));
                label.setOnMousePressed(mouseEvent -> {
                    selectedMachineDetails = entry.getValue();
                    selectedExtra = null;
                    selectedWorkerDetails = null;
                    selectedBuildingDetails = null;
                });
                objectBox.getChildren().add(label);
            }
        } else {
            objectBox.getChildren().remove(0, objectBox.getChildren().size());
            ImageView imageView;
            Image image;
            Label label;
            for (Map.Entry<String, WorkerDetails> entry : map.entrySet()) {
                image = new Image(entry.getKey());
                imageView = new ImageView(image);
                imageView.setFitWidth(90);
                imageView.setFitHeight(70);
                label = new Label(null, imageView);
                label.setStyle("-fx-border-color: black;");
                label.setOnMousePressed(mouseEvent -> {
                    selectedWorkerDetails = entry.getValue();
                    selectedBuildingDetails = null;
                    selectedMachineDetails = null;
                    selectedExtra = null;
                });
                objectBox.getChildren().add(label);
            }
        }
    }


    public void setMapController(MapController mapController) {
        this.mapController = mapController;
        mapControllerStatic = mapController;
    }

    private void selectCell(double x, double y, double x2, double y2) {
        if (Math.abs(x - x2) < 0.5 && Math.abs(y - y2) < 0.5) {
            int xInt = (int) (x / CELL_SIZE);
            int yInt = (int) (y / CELL_SIZE);
            selectedX1 = selectedX2 = (xInt + currentX - 1);
            selectedY1 = selectedY2 = (yInt + currentY - 1);
            showMap(showingMap);
            miniMap();
            System.out.println("test!");
            return;
        }
        selectedX1 = (int) ((x / CELL_SIZE) + currentX - 1);
        selectedX2 = (int) ((x2 / CELL_SIZE) + currentX - 1);
        selectedY1 = (int) (y / CELL_SIZE + currentY - 1);
        selectedY2 = (int) (y2 / CELL_SIZE + currentY - 1);
        int maxX = Math.max(selectedX2, selectedX1);
        int minX = Math.min(selectedX2, selectedX1);
        int maxY = Math.max(selectedY2, selectedY1);
        int minY = Math.min(selectedY1, selectedY2);
        selectedX1 = minX;
        selectedY1 = minY;
        selectedX2 = maxX;
        selectedY2 = maxY;
        showMap(showingMap);
        miniMap();
    }


    public void save() {
        mapController.saveMap();
    }

    public void back() {
        if (staticMainMenu != null)
            staticMainMenu.start(StartingMenu.mainStage);
        else {
            mainMenu = new MainMenu();
            mainMenu.setMapController(mapControllerStatic);
            mainMenu.start(StartingMenu.mainStage);
        }
    }


    public void goRight() {
        if (currentX < mapController.getMap().length - 8)
            currentX++;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY));
        miniMap();
    }

    public void goLeft() {
        if (currentX > 1)
            currentX--;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY));
        miniMap();
    }

    public void zoomIn() {
        if (System.currentTimeMillis() - currentTimeZoom < 700)
            return;
        if (CELL_SIZE <= 95)
            CELL_SIZE += 5;
        if (CELL_SIZE == 85)
            CELL_SIZE = 90;
        currentTimeZoom = System.currentTimeMillis();
        showMap(showingMap);
    }

    public void zoomOut() {
        if (System.currentTimeMillis() - currentTimeZoom < 700)
            return;
        if (CELL_SIZE >= 80)
            CELL_SIZE -= 5;
        if (CELL_SIZE == 85)
            CELL_SIZE = 80;
        currentTimeZoom = System.currentTimeMillis();
        showMap(showingMap);
    }

    public void goUp() {
        if (currentY > 1)
            currentY--;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY));
        miniMap();
    }

    public void goDown() {
        if (currentY < mapController.getMap().length - 8)
            currentY++;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY));
        miniMap();
    }


    private void raiseError(MapMessages messages) {//raise error
        // repaired!
        errorLabel.setText(messages.toString());
        if (!mainPane.getChildren().contains(errorLabel))
            mainPane.getChildren().add(errorLabel);
    }

    private void showMoveStage(int x, int y) {//80px normal 64px selected!
        System.out.println("showMoveStage is called!");
        Stage moveUnitStage = new Stage();
        //buttons
        Button moveButton = new Button("move");
        moveButton.relocate(150, 360);
        Button resetMove = new Button("reset");
        resetMove.relocate(250, 300);
        //scroll pane
        ScrollPane scrollPane1 = new ScrollPane();
        scrollPane1.relocate(50, 60);
        scrollPane1.resize(80, 300);
        scrollPane1.setMaxWidth(300);
        scrollPane1.setMinHeight(97);
        SpinnerValueFactory<Integer> factoryValueX = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                mapController.getMap().length + 1);
        Spinner<Integer> spinnerX = new Spinner<>(factoryValueX);
        SpinnerValueFactory<Integer> factoryValueY = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                mapController.getMap().length + 1);
        Spinner<Integer> spinnerY = new Spinner<>(factoryValueY);
        spinnerX.relocate(100, 280);
        spinnerX.setMaxWidth(60);
        spinnerY.setMaxWidth(60);
        spinnerY.relocate(300, 280);
        spinnerX.setEditable(true);
        spinnerY.setEditable(true);
        HBox hBox = new HBox();
        scrollPane1.setContent(hBox);
        ArrayList<Worker> people;
        if (x <= selectedX2 && x >= selectedX1 && y <= selectedY2 && y >= selectedY1) {
            people = new ArrayList<>();
            for (int i1 = selectedX1; i1 <= selectedX2; i1++)
                for (int i2 = selectedY1; i2 <= selectedY2; i2++)
                    people.addAll(mapController.getCell(i1, i2).getPeople());//more than one cell
        } else
            people = mapController.getCell(x, y).getPeople();//for selecting one cell
        for (Worker person : people) {
            if (person.getGovernment() == gameController.getCurrentGovernment() &&
                    person.getWorkerDetails().getSpeed() != 0 && person.getWorkPlace() == null) {
                ImageView view = new ImageView(person.getWorkerDetails().getImagePath());
                Label tempLabel = new Label(null, view);
                tempLabel.setPrefSize(person.getIsGoingToMoveGui() ? 64 : 80, person.getIsGoingToMoveGui() ? 64 : 80);
                tempLabel.setTooltip(new Tooltip(person.getWorkerDetails().getName() + "\n" +
                        person.getPosition().toString() + "\n" + "hitpoint: " + person.getHitPoint()));
                tempLabel.setAlignment(Pos.CENTER);
                tempLabel.setOnMouseClicked(mouseEvent -> {
                    person.setGoingToMoveGui(!person.getIsGoingToMoveGui());
                    if (tempLabel.getWidth() > 75) {
                        tempLabel.setPrefWidth(64);
                        tempLabel.setPrefHeight(64);
                    } else {
                        tempLabel.setPrefHeight(80);
                        tempLabel.setPrefWidth(80);//to show selection!
                    }
                });
                hBox.getChildren().add(tempLabel);
            }
        }
        ArrayList<Worker> finalPeople = people;

        resetMove.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                for (Worker worker : finalPeople) {
                    worker.setGoingToMoveGui(false);
                    worker.setDestination(worker.getPosition());
                    moveUnitStage.close();
                }
        });
        moveButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                destinationX = spinnerX.getValue();
                destinationY = spinnerY.getValue();
                move(finalPeople);
                moveUnitStage.close();
            }
        });
        //some code
        Pane pane = new Pane(moveButton, scrollPane1, spinnerX, spinnerY, resetMove);
        pane.setStyle("-fx-background-color: #ac8fb1;");
        Scene scened = new Scene(pane, 400, 400);
        moveUnitStage.setScene(scened);
        if (!hBox.getChildren().isEmpty())
            moveUnitStage.show();
        else
            moveUnitStage.close();
    }

    private void move(ArrayList<Worker> workers) {
        if (workers == null)
            return;
        guiWorker = workers;
        for (Worker worker : workers)
            if (worker.getIsGoingToMoveGui()) {
                worker.setDestination(mapController.getCell(destinationX, destinationY));//
                worker.setGoingToMoveGui(false);//ready for next turn!
            }
    }

    private void removeObjects() {
        for (int i1 = selectedX1; i1 <= selectedX2; i1++)
            for (int i2 = selectedY1; i2 <= selectedY2; i2++)
                mapController.removeBuilding(i1, i2);
        showMap(showingMap);
        miniMap();
    }


    private void nextTurn() throws IOException {//called by pressing 'n'
        if (!isInGame)
            return;
        System.out.println("next turn is called!");
        gameController.nextTurn();//go to next turn
        updateDetailsBox();
        updateResourcesBox();
        updateObjectBox(VboxCreator.CASTLE_BUILDINGS);//make it default
        showMap(showingMap);//update map
        miniMap();
        if (guiWorker != null) {
            for (Worker myWorker : guiWorker)
                myWorker.setGoingToMoveGui(false);
        }
        StartingMenu.getDOut().writeObject("next " + gameController.getCurrentGovernment().getLord().getUserName());
        getChanges.canMakeChanges = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.relocate(900, 200);
        if (gameController == null) {
            gameController = staticGameController;
        }
        if (mapControllerStatic == null) {
            if (MainMenu.staticMapController != null) {
            } else {
                MainMenu.staticMapController = new MapController();
            }
            mapController = MainMenu.staticMapController;
            mapControllerStatic = mapController;
        } else {
            mapController = mapControllerStatic;
        }

        getChanges = new GetChanges(this);
        getChanges.setGameController(staticGameController);
        getChanges.start();

        textureItem = new String[GroundTexture.values().length];
        for (int i = 0; i < textureItem.length; i++)
            textureItem[i] = GroundTexture.values()[i].getName();
        if (!isInGame) initExtra();
        else initGame();
        cellPane.setOnScroll(scrollEvent -> {
            if (scrollEvent.getDeltaY() < 1 && scrollEvent.getDeltaY() > -1) {
                return;
            }
            if (scrollEvent.getDeltaY() > 0) {
                zoomIn();
            } else {
                zoomOut();
            }
        });

        mainPane.setOnMouseDragReleased(mouseDragEvent -> {
            double xPane = mouseDragEvent.getX() - X_CELL_PANE, yPane = mouseDragEvent.getY() - Y_CELL_PANE;
            if (xPane >= 600 || xPane < 0 || yPane >= 600 || yPane < 0) {
                return;
            }
            if (!(XFullDrag >= 600 || XFullDrag < 0 || YFullDrag >= 600 || YFullDrag < 0)) {
                return;
            }
            if (selectedExtra != null) {
                if (isDraggedExtra) {
                    isDraggedExtra = false;
                    MapMessages messages = mapController.setExtra(currentX + (int) (xPane / CELL_SIZE) - 1,
                            currentY + (int) (yPane / CELL_SIZE) - 1, selectedExtra);
                    if (messages != MapMessages.SUCCESS) {
                        raiseError(messages);
                    } else {
                        raiseError(MapMessages.NULL_MESSAGE);
                    }
                    showMap(showingMap);
                    miniMap();
                    selectedExtra = null;
                    return;
                }
                MapMessages messages = mapController.setExtra(currentX + (int) (xPane / CELL_SIZE) - 1,
                        currentY + (int) (yPane / CELL_SIZE) - 1, selectedExtra);
                if (messages != MapMessages.SUCCESS) {
                    raiseError(messages);
                }
                showMap(showingMap);
                miniMap();
                selectedExtra = null;
                return;
            } else if (selectedBuildingDetails != null) {
                if (getChanges.canMakeChanges) {
                    GameMessage gameMessage = gameController.checkDropBuilding(currentX + (int) (xPane / CELL_SIZE) - 1,
                            currentY + (int) (yPane / CELL_SIZE) - 1, selectedBuildingDetails.getName());
                    alert(gameMessage);
                    showMap(showingMap);
                    miniMap();
                    updateDetailsBox();
                    updateResourcesBox();
                    try {
                        if (gameMessage.equals(GameMessage.SUCCESS))
                            StartingMenu.getDOut().writeObject(new DropBuilding(selectedBuildingDetails,
                                    currentX + (int) (xPane / CELL_SIZE) - 1,
                                    currentY + (int) (yPane / CELL_SIZE) - 1
                            ));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    selectedBuildingDetails = null;
                } else {
                    System.out.println("Not your Turn");
                }
            } else if (selectedWorkerDetails != null) {
                if (getChanges.canMakeChanges) {
                    GameMessage gameMessage = gameController.checkMakeTroop(selectedWorkerDetails.getName(), 1,
                            currentX + (int) (xPane / CELL_SIZE) - 1,
                            currentY + (int) (yPane / CELL_SIZE) - 1);
                    alert(gameMessage);
                    System.out.println(gameMessage);
                    updateDetailsBox();
                    updateResourcesBox();
                    showMap(showingMap);
                    miniMap();
                    try {
                        if (gameMessage.equals(GameMessage.SUCCESS))
                            StartingMenu.getDOut().writeObject(new MakeTroop(selectedWorkerDetails,
                                    currentX + (int) (xPane / CELL_SIZE) - 1,
                                    currentY + (int) (yPane / CELL_SIZE) - 1
                            ));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    selectedWorkerDetails = null;
                } else System.out.println("Not Your Turn");
            } else {
                Cell cell = mapController.getCell(currentX + (int) (xPane / CELL_SIZE) - 1,
                        currentY + (int) (yPane / CELL_SIZE) - 1);
                if (cell != null && cell.getBuilding() != null) {
                    switch (cell.getBuilding().getBuildingsDetails()) {
                        case BARRACKS -> updateObjectBoxTroops(VboxCreator.EUROPEAN_SOLDIERS);
                        case MERCENARY_POST -> updateObjectBoxTroops(VboxCreator.ARABIAN_SOLDIERS);
                        case ENGINEERS_GUILD -> updateObjectBoxTroops(VboxCreator.ENGINEERS_GUILD);
                    }
                }
            }
            isStartDrag = false;

        });
        mainPane.setOnKeyPressed(keyEvent -> {// for handling shortcuts!
            if (keyEvent.getCode() == KeyCode.S) {
                save();
            } else if (keyEvent.getCode() == KeyCode.Q) {
                back();
            } else if (keyEvent.getCode() == KeyCode.N) {
                try {
                    nextTurn();//for going to next turn
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (keyEvent.getCode() == KeyCode.G) {
                gotoXY();
            } else if (keyEvent.getCode() == KeyCode.L) {
                goLeft();
            } else if (keyEvent.getCode() == KeyCode.R) {
                goRight();
            } else if (keyEvent.getCode() == KeyCode.U) {
                goUp();
            } else if (keyEvent.getCode() == KeyCode.D) {
                goDown();
            } else if (keyEvent.getCode() == KeyCode.P) {
                gameController.printAllGovernments();
            } else if (keyEvent.getCode() == KeyCode.E) {
                removeObjects();
            } else if (keyEvent.getCode() == KeyCode.C) {
                copyBuilding();
            } else if (keyEvent.getCode() == KeyCode.V) {
                pasteBuilding();
            } else if (keyEvent.getCode() == KeyCode.T) {
                showTaxStage();
            }
            else if (keyEvent.getCode() == KeyCode.Y)
                startShop();
        });
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        if (!mapController.isInitState()) {
            mapController.loadMapNormal();
        }
        showMap(mapController.showMapGui(currentX, currentY));
        miniMap();
        cellPane.setOnDragDetected(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.MIDDLE || mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                    startMoveX = mouseEvent.getX();
                    startMoveY = mouseEvent.getY();
                }
                return;
            }
            isStartDrag = true;
            startDragX = mouseEvent.getX();
            startDragY = mouseEvent.getY();
        });
        mainPane.setOnDragDetected(mouseEvent -> {
            System.out.println(mouseEvent.getX());
            XFullDrag = mouseEvent.getX() - X_CELL_PANE;
            YFullDrag = mouseEvent.getY() - Y_CELL_PANE;
            mainPane.startFullDrag();
        });
        cellPane.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.MIDDLE || mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                    double deltaX = mouseEvent.getX() - startMoveX;
                    double deltaY = mouseEvent.getY() - startMoveY;
                    if (deltaX > CELL_SIZE)
                        goLeft();
                    if (deltaX < -1 * CELL_SIZE)
                        goRight();
                    if (deltaY > CELL_SIZE)
                        goDown();
                    if (deltaY < -1 * CELL_SIZE)
                        goUp();
                }
                return;
            }
            if (selectedExtra != null) {
                if (isDraggedExtra) {
                    isDraggedExtra = false;
                    MapMessages messages = mapController.setExtra(currentX + (int) (mouseEvent.getX() / CELL_SIZE) - 1,
                            currentY + (int) (mouseEvent.getY() / CELL_SIZE) - 1, selectedExtra);
                    if (messages != MapMessages.SUCCESS) {
                        raiseError(messages);
                    } else {
                        raiseError(MapMessages.NULL_MESSAGE);
                    }
                    showMap(showingMap);
                    miniMap();
                    selectedExtra = null;
                    return;
                }
                isDraggedExtra = false;
                MapMessages messages = mapController.setExtra(currentX + (int) (mouseEvent.getX() / CELL_SIZE) - 1,
                        currentY + (int) (mouseEvent.getY() / CELL_SIZE) - 1, selectedExtra);
                if (messages != MapMessages.SUCCESS) {
                    raiseError(messages);
                }
                showMap(showingMap);
                miniMap();
                selectedExtra = null;
                return;
            } else if (selectedBuildingDetails != null) {
                if (getChanges.canMakeChanges) {
                    GameMessage gameMessage = gameController.checkDropBuilding(currentX + (int) (mouseEvent.getX() / CELL_SIZE) - 1,
                            currentY + (int) (mouseEvent.getY() / CELL_SIZE) - 1, selectedBuildingDetails.getName());
                    alert(gameMessage);
                    showMap(showingMap);
                    miniMap();
                    updateDetailsBox();
                    updateResourcesBox();
                    try {
                        if (gameMessage.equals(GameMessage.SUCCESS))
                            StartingMenu.getDOut().writeObject(new DropBuilding(selectedBuildingDetails,
                                    currentX + (int) (mouseEvent.getX() / CELL_SIZE) - 1,
                                    currentY + (int) (mouseEvent.getY() / CELL_SIZE) - 1
                            ));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    selectedBuildingDetails = null;
                } else System.out.println("Not your Turn");
            } else if (selectedWorkerDetails != null) {
                GameMessage gameMessage = gameController.checkMakeTroop(selectedWorkerDetails.getName(), 1,
                        currentX + (int) (mouseEvent.getX() / CELL_SIZE) - 1,
                        currentY + (int) (mouseEvent.getY() / CELL_SIZE) - 1);
                alert(gameMessage);
                System.out.println(gameMessage);
                updateDetailsBox();
                updateResourcesBox();
                showMap(showingMap);
                miniMap();
                try {
                    if (gameMessage.equals(GameMessage.SUCCESS))
                        StartingMenu.getDOut().writeObject(new MakeTroop(selectedWorkerDetails,
                                currentX + (int) (mouseEvent.getX() / CELL_SIZE) - 1,
                                currentY + (int) (mouseEvent.getY() / CELL_SIZE) - 1
                        ));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                selectedWorkerDetails = null;
            } else {
                Cell cell = mapController.getCell(currentX + (int) (mouseEvent.getX() / CELL_SIZE) - 1,
                        currentY + (int) (mouseEvent.getY() / CELL_SIZE) - 1);
                if (cell != null && cell.getBuilding() != null) {
                    switch (cell.getBuilding().getBuildingsDetails()) {
                        case BARRACKS -> updateObjectBoxTroops(VboxCreator.EUROPEAN_SOLDIERS);
                        case MERCENARY_POST -> updateObjectBoxTroops(VboxCreator.ARABIAN_SOLDIERS);
                        case ENGINEERS_GUILD -> updateObjectBoxTroops(VboxCreator.ENGINEERS_GUILD);
                    }
                }
            }
            if (!isStartDrag) {
                selectCell(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                return;
            }
            selectCell(startDragX, startDragY, mouseEvent.getX(), mouseEvent.getY());
            isStartDrag = false;
        });
        mainPane.setOnMouseReleased(mouseEvent -> {
            isDraggedExtra = false;
            isStartDrag = false;
        });
    }

    private void pasteBuilding() {
        if (gameController.getCopyBuildingDetail() == null)
            alert(GameMessage.NOTHING_TO_PASTE);
        else if (selectedY1 != selectedY2 || selectedX1 != selectedX2)
            alert(GameMessage.UNABLE_TO_PASTE);
        else {
            String name = gameController.getCopyBuildingDetail().getName();
            alert(gameController.checkDropBuilding(selectedX1, selectedY1, name));
            showMap(showingMap);
            miniMap();
        }
    }

    private void showTaxStage() {
        if (!isInGame)
            return;
        Stage taxStage = new Stage();
        taxStage.setTitle("tax");
        taxStage.setResizable(false);
        Slider fearRateSlide = new Slider();
        SpinnerValueFactory<Integer> taxFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-3,8,
                gameController.getCurrentGovernment().getTaxRate());
        Spinner<Integer> taxSpinner = new Spinner<>(taxFactory);
        SpinnerValueFactory<Integer> foodFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-2,2,
                gameController.getCurrentGovernment().getFoodRate());
        Spinner<Integer> foodSpinner = new Spinner<>(foodFactory);

        taxSpinner.setEditable(true);
        taxSpinner.setMaxWidth(60);
        taxSpinner.relocate(130,0);
        foodSpinner.setMaxWidth(60);
        foodSpinner.relocate(130,0);
        fearRateSlide.setMax(2.1);
        fearRateSlide.setMin(-2);
        fearRateSlide.setValue(gameController.getCurrentGovernment().getFearRate() + 0.001);
        fearRateSlide.relocate(115,0);
        Government currentGovernment = gameController.getCurrentGovernment();

        Pane paneTax = getPane("tax",currentGovernment,20,"file:" + MY_PATH +
                "/src/main/resources/images/tax_stage/tax.png");
        Pane paneFear = getPane("fear",currentGovernment,60,"file:" + MY_PATH +
                "/src/main/resources/images/tax_stage/fear.png");
        Pane paneFood = getPane("food",currentGovernment,100,"file:" + MY_PATH +
                "/src/main/resources/images/tax_stage/tax.png");

        paneFear.getChildren().add(fearRateSlide);
        paneTax.getChildren().add(taxSpinner);

        fearRateSlide.getProperties().addListener((MapChangeListener<? super Object, ? super Object>) observable ->
                ((Label)paneFear.getChildren().get(0)).setText("fear rate: "+(int)fearRateSlide.getValue()));
        taxSpinner.getProperties().addListener((MapChangeListener<? super Object, ? super Object>) observable ->
                ((Label)paneTax.getChildren().get(0)).setText("tax rate : " + taxSpinner.getValue()));
        foodSpinner.getProperties().addListener((MapChangeListener<? super Object, ? super Object>) observable ->
                ((Label)paneFood.getChildren().get(0)).setText("food rate : " + foodSpinner.getValue()));

        Button closeButton = new Button("save");
        closeButton.relocate(130, 160);
        closeButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                currentGovernment.setTaxRate(taxSpinner.getValue());
                currentGovernment.setFearRate((int)Math.floor(fearRateSlide.getValue()));
                currentGovernment.setFoodRate(0);//repair
                taxStage.close();
            }
        });

        Pane pane = new Pane(paneFood, paneTax, paneFear, closeButton);
        Scene tempScene = new Scene(pane, 300, 200);
        taxStage.setScene(tempScene);
        taxStage.show();
    }

    private Pane getPane(String type, Government currentGovernment, int Y , String fullPath) {
        Label label = switch (type) {
            case ("tax") -> new Label("tax rate:" + currentGovernment.getTaxRate());
            case ("fear") -> new Label("fear rate: " + currentGovernment.getFearRate());
            default -> new Label("food rate: " + currentGovernment.getFoodRate());
        };
        label.relocate(30,0);
        ImageView iv = new ImageView(fullPath);
        iv.setFitWidth(20);
        iv.setFitHeight(20);
        Pane pane = new Pane(label,iv);
        if (type.equals("food"))
            pane.setStyle("-fx-background-color: green;");
        else
            pane.setStyle("-fx-background-color: red;");

        pane.relocate(20,Y);
        pane.setMinWidth(260);
        return pane;
    }

    private void alert(GameMessage gameMessage) {
        if (gameMessage.equals(GameMessage.SUCCESS)) return;
        Text text = new Text(420, 700, gameMessage.toString());
        text.setFill(Color.RED);
        text.setFont(Font.font(20));
        mainPane.getChildren().add(text);
        new Timeline(new KeyFrame(Duration.millis(2000),
                actionEvent -> mainPane.getChildren().remove(text))).play();
    }

    public void gotoXY() {
        Stage goStage = new Stage();
        Pane thisPane = new Pane();
        Scene scene = new Scene(thisPane, 320, 200);
        thisPane.setStyle("-fx-background-color : yellow");
//        scene.getStylesheets().add(pathCssFile);
        goStage.setScene(scene);
        Spinner<Integer> xSpinner = new Spinner<>();
        Spinner<Integer> ySpinner = new Spinner<>();
        xSpinner.setMaxWidth(86);
        ySpinner.setMaxWidth(86);
        xSpinner.setEditable(true);
        ySpinner.setEditable(true);
        SpinnerValueFactory<Integer> XFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                mapController.getMap().length, mapController.getMap().length / 2);
        SpinnerValueFactory<Integer> YFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                mapController.getMap().length, mapController.getMap()[0].length / 2);
        xSpinner.setValueFactory(XFactory);
        ySpinner.setValueFactory(YFactory);
        Button go = new Button("go");
        go.relocate(100, 120);
        xSpinner.relocate(50, 30);
        ySpinner.relocate(200, 30);
        go.setOnMouseClicked(mouseEvent -> {
            currentX = xSpinner.getValue() - 1;
            currentY = ySpinner.getValue() - 1;
            showMap(mapController.showMapGui(xSpinner.getValue() - 1, ySpinner.getValue() - 1));
            miniMap();
            goStage.close();
        });
        thisPane.getChildren().addAll(go, xSpinner, ySpinner);
        goStage.show();
    }

    public void initExtra() {
        objectBox.getChildren().remove(0, objectBox.getChildren().size());
        ImageView imageView;
        Image image;
        Label label;
        String basePath = new File("").getAbsolutePath();
        for (int i = 0; i < Extras.values().length; i++) {
            image = new Image("file:" + basePath + "/src/main/resources/ExtraImage/" + Extras.values()[i].getImagePath());
            imageView = new ImageView(image);
            imageView.setFitWidth(90);
            imageView.setFitHeight(70);
            int finalI = i;
            label = new Label(null, imageView);
            label.setStyle("-fx-border-color: black;");
            label.setOnMousePressed(mouseEvent -> {
                System.out.println(finalI);
                selectedExtra = Extras.values()[finalI];
                isDraggedExtra = true;
            });
            objectBox.getChildren().add(label);
        }
    }


    private void setTexture(int x1, int y1, int x2, int y2) {//use function this way: x1 <= x2 && y1 <= y2
        Label label = new Label("");
        ChoiceBox<String> textureBox = new ChoiceBox<>();
        textureBox.getItems().addAll(textureItem);
        textureBox.setValue(textureItem[0]);
        textureBox.relocate(130, 70);
        Stage stage = new Stage();
        stage.initOwner(mainStage);
        stage.setResizable(false);
        stage.setTitle("texture");
        Pane pane = new Pane();
        Scene myScene = new Scene(pane);
        myScene.getStylesheets().add(pathCssFile);
        stage.setScene(myScene);
        Button clearButton = new Button("clear");
        clearButton.relocate(200, 100);
        pane.getChildren().add(clearButton);
        pane.getChildren().add(textureBox);
        clearButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                for (int i1 = x1; i1 <= x2; i1++)
                    for (int i2 = y1; i2 <= y2; i2++)
                        mapController.clear(i1, i2);
                showMap(showingMap);
                miniMap();
                stage.close();
            }
        });
        Button change = new Button("save");
        change.relocate(100, 100);
        pane.getChildren().add(change);
        change.setOnMouseClicked(mouseEvent -> {
            MapMessages messages;
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                messages = mapController.setTexture(x1, x2, y1, y2, textureBox.getValue());
                showMap(showingMap);
                miniMap();
                if (messages == MapMessages.SUCCESS)
                    stage.close();
                else {
                    label.setText(messages.toString());
                    label.setAlignment(Pos.CENTER);
                    label.setLayoutX(130);
                    label.setLayoutY(10);
                    pane.getChildren().add(label);
                }
            }
        });
        stage.show();
    }

    public void miniMap() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (miniMap != null && miniMap.getChildren() != null)
                    miniMap.getChildren().remove(0, miniMap.getChildren().size());
                else miniMap = new Pane();
                int startX = 0, startY = 0, centerX = currentX + 3, centerY = currentY + 3;
                if (centerX < 10 && centerY < 10) ;
                else if (centerX < 10) startY = centerY - 10;
                else if (centerY < 10) startX = centerX - 10;
                else if (centerX > 190 && centerY > 190) startY = startX = 180;
                else if (centerX > 190) {
                    startX = 180;
                    startY = centerY - 10;
                } else if (centerY > 190) {
                    startX = centerX - 10;
                    startY = 180;
                } else {
                    startX = centerX - 10;
                    startY = centerY - 10;
                }
                for (int i1 = startX, x = 0; i1 < startX + 20; i1++, x++)
                    for (int i2 = startY, y = 0; i2 < startY + 20; i2++, y++) {
                        Label label = mapController.getCell(i1, i2).toLabel(MINI_MAP_SIZE * x, MINI_MAP_SIZE * y, MINI_MAP_SIZE);
                        miniMap.getChildren().add(label);
                    }
            }
        });

    }

    @Override
    public void run() {
    }

    public void showOriginalMap() {
        showMap(showingMap);
    }

    public void dropBuilding(DropBuilding dropBuilding) {
        gameController.dropOpponentBuilding(dropBuilding);
        showMap(showingMap);
        miniMap();
    }

    public void makeTroop(MakeTroop makeTroop) {
        gameController.makeOpponentTroop(makeTroop);
        showMap(showingMap);
        miniMap();
    }
}
