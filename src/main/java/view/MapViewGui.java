package view;

import controller.MapController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Cell;
import model.User;
import model.generalenums.Extras;
import view.message.MapMessages;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapViewGui extends Application implements Initializable {
    private final static String pathCssFile = "file:" + (new File("").getAbsolutePath()) +
            "/src/main/resources/CSS/Texture.css";
    private double startDragX = 0, startDragY = 0;
    private int CELL_SIZE = 75;
    @FXML
    private Pane cellPane;
    @FXML
    private AnchorPane mainPane;
    private Cell[][] showingMap;
    private int currentX = 5, currentY = 5;
    public MapController mapController = new MapController();
    private boolean isStartDrag = false;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox objectBox;
    private int selectedX1 = -1, selectedX2 = -1, selectedY1 = -1, selectedY2 = -1;//-1 means there is no selected cell!
    private boolean isDraggedExtra = false;
    private Extras selectedExtra;
    private Stage mainStage;
    //    Alert alert = new Alert(Alert.AlertType.ERROR);
    private MainMenu mainMenu;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.mainStage = stage;
        try {
            Parent parent1 = FXMLLoader.load(MapController.class.getResource("/FXML/Map.fxml"));
            Scene mapScene = new Scene(parent1);
            stage.setScene(mapScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMap(Cell[][] myMap, int size) {
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
                label.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent contextMenuEvent) {
                        isDraggedExtra = false;
                        System.out.println((currentX + finalI - 1));
                        if ((currentX + finalI - 1) <= selectedX2 && (currentX + finalI - 1) >= selectedX1 &&
                                (currentY + finalI1 - 1) <= selectedY2 && (currentY + finalI1 - 1) >= selectedY1)
                            setTexture(selectedX1, selectedY1, selectedX2, selectedY2);
                        else
                            setTexture(currentX + finalI - 1, currentY + finalI1 - 1,
                                    currentX + finalI + 1, currentY + finalI1 - 1);

                    }
                });
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

    private void selectCell(double x, double y, double x2, double y2) {
        if (Math.abs(x - x2) < 0.5 && Math.abs(y - y2) < 0.5) {
            int xInt = (int) (x / CELL_SIZE);
            int yInt = (int) (x / CELL_SIZE);
            selectedX1 = selectedX2 = (xInt + currentX - 1);
            selectedY1 = selectedY2 = (yInt + currentY - 1);
            showMap(showingMap, 10);
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
        showMap(showingMap, 10);
    }

    private void moveMap(int x, int y) {
        System.out.println("(" + x + "," + y + ")");
    }

    public void save() {
        if (user != null)
            mapController.saveMap(user.getUserName());
        else
            mapController.saveMap("default");
    }

    public void back() {

    }

    public void resetFields() {
        if (cellPane.getChildren().size() > 1)
            cellPane.getChildren().remove(0, cellPane.getChildren().size() - 1);
    }

    public void goRight() {
        if (currentX < mapController.getMap().length - 8)
            currentX++;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY), 10);
    }

    public void goLeft() {
        if (currentX > 1)
            currentX--;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY), 10);
    }

    public void zoomIn() {
        if (CELL_SIZE <= 95)
            CELL_SIZE += 5;
        if (CELL_SIZE == 85)
            CELL_SIZE = 90;
        showMap(showingMap, 100);
    }

    public void zoomOut() {
        if (CELL_SIZE >= 80)
            CELL_SIZE -= 5;
        if (CELL_SIZE == 85)
            CELL_SIZE = 80;
        showMap(showingMap, 100);
    }

    public void goDown() {
        if (currentY > 1)
            currentY--;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY), 10);
    }

    public void goUp() {
        if (currentY < mapController.getMap().length - 8)
            currentY++;
        else
            return;
        showMap(mapController.showMapGui(currentX, currentY), 10);
    }


    private void raiseError(MapMessages messages) {//raise error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error!");
        alert.setContentText(null);
        alert.setContentText(messages.toString());
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initExtra();
        mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {// for handling shortcuts!
                if (keyEvent.getCode() == KeyCode.S)
                    save();
                else if (keyEvent.getCode() == KeyCode.Q)
                    back();
                else if (keyEvent.getCode() == KeyCode.N)
                    System.out.println("next turn!");//for going to next turn
                else if (keyEvent.getCode() == KeyCode.G)
                    gotoXY();
                else if (keyEvent.getCode() == KeyCode.L)
                    goLeft();
                else if (keyEvent.getCode() == KeyCode.R)
                    goRight();
                else if (keyEvent.getCode() == KeyCode.U)
                    goUp();
                else if (keyEvent.getCode() == KeyCode.D)
                    goDown();
            }
        });
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapController.initializeMap(400, true);
        showMap(mapController.showMapGui(currentX, currentY), 75);

        cellPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println(keyEvent.getCharacter());
            }
        });
        cellPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isStartDrag = true;
                startDragX = mouseEvent.getX();
                startDragY = mouseEvent.getY();
                int xShow = (int) (mouseEvent.getX() / CELL_SIZE);
                int yShow = (int) (mouseEvent.getY() / CELL_SIZE);
                System.out.println(xShow);
                System.out.println(yShow);
            }
        });

        cellPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isDraggedExtra) {
                    isDraggedExtra = false;
                    MapMessages messages = mapController.setExtra(currentX + (int) (mouseEvent.getX() / CELL_SIZE) - 1,
                            currentY + (int) (mouseEvent.getY() / CELL_SIZE) - 1, selectedExtra);
                    if (messages != MapMessages.SUCCESS) {
                        raiseError(messages);
                    }
                    showMap(showingMap, 8);
                    return;
                }
                if (!isStartDrag) {
                    selectCell(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
                    return;
                }
                selectCell(startDragX, startDragY, mouseEvent.getX(), mouseEvent.getY());
                isStartDrag = false;
            }
        });
        mainPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isDraggedExtra = false;
                isStartDrag = false;
            }
        });
    }

    public void gotoXY() {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("go to Cell");
//        alert.setHeaderText(null);
//        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.getChildren().remove(0);
//        TextInputDialog textField = new TextInputDialog();
//        TextField textField1 = new TextField();
//        textField1.setLayoutX(15);
//        textField1.setLayoutY(15);
//        textField1.setVisible(true);
//        textField1.setPromptText("ali");
//        alert.getDialogPane().getChildren().add(textField1);
//        alert.showAndWait();
        Stage goStage = new Stage();
        System.out.println("gotoXY is called!");
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
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println(finalI);
                    selectedExtra = Extras.values()[finalI];
                    isDraggedExtra = true;
                }
            });
            objectBox.getChildren().add(label);
        }
    }

    private void setTexture(int x1, int y1, int x2, int y2) {//use function this way: x1 <= x2 && y1 <= y2
        Stage stage = new Stage();
        stage.initOwner(mainStage);
        stage.setResizable(false);
        stage.setTitle("texture");
        Pane pane = new Pane();
        Scene myScene = new Scene(pane);
        myScene.getStylesheets().add(pathCssFile);
        stage.setScene(myScene);
        Button button = new Button("clear");
        button.relocate(200, 100);
        pane.getChildren().add(button);
        button.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                for (int i1 = x1; i1 <= x2; i1++)
                    for (int i2 = y1; i2 <= y2; i2++)
                        mapController.clear(i1, i2);
                showMap(showingMap, 10);
                stage.close();
            }
        });
        stage.show();
    }

    public void clear() {
        for (int i = selectedX1; i < selectedX2; i++)
            for (int j = selectedY1; j < selectedY2; j++)
                mapController.clear(i, j);
        showMap(showingMap, 10);
    }

}
