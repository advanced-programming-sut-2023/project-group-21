package view;

import controller.MapController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapViewGui extends Application implements Initializable {
    private final int CELL_SIZE = 75;
    @FXML
    private Pane cellPane;
    public MapController mapController = new MapController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent parent1 = FXMLLoader.load(MapController.class.getResource("/FXMLs/Map.fxml"));
            Scene mapScene = new Scene(parent1);
            stage.setScene(mapScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMap(Cell[][] myMap) {
        if (cellPane.getChildren().size() > 1)
            cellPane.getChildren().remove(0, cellPane.getChildren().size() - 1);
        for (int i1 = 0; i1 < myMap.length; i1++)
            for (int i2 = 0; i2 < myMap.length; i2++) {
                Label label = myMap[i1][i2].toLabel(CELL_SIZE * i1, CELL_SIZE * i2);
                int finalI = i1;
                int finalI1 = i2;
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectCell(finalI, finalI1);
                    }
                });
                cellPane.getChildren().add(label);
            }
    }

    private void selectCell(int x, int y) {
        System.out.println(x + "    " + y);
    }

    private void moveMap(int x, int y) {
        System.out.println("(" + x + "," + y + ")");
    }

    public void save() {

    }

    public void back() {

    }

    public void resetFields() {
        if (cellPane.getChildren().size() > 1)
            cellPane.getChildren().remove(0, cellPane.getChildren().size() - 1);
    }

    public void goLeft() {

    }

    public void goRight() {

    }

    public void goDown() {

    }

    public void goUp() {

    }

    public void refreshMap() {

    }

    public void selectUnit() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mapController.initializeMap(400, true);
        showMap(mapController.showMapGui(5, 5));
        cellPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int xShow = (int) (mouseEvent.getX() / CELL_SIZE);
                int yShow = (int) (mouseEvent.getY() / CELL_SIZE);
                System.out.println(xShow);
                System.out.println(yShow);
            }
        });
        cellPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(mouseEvent);
                System.out.println(mouseEvent.getSceneX() + " jjj");
                System.out.println(mouseEvent.getSceneY() + " jjj");
            }
        });
    }

    public void gotoXY(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("go to Cell");
        alert.setHeaderText(null);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getChildren().remove(0);
        TextInputDialog  textField = new TextInputDialog();
        TextField textField1 = new TextField();
        textField1.setLayoutX(15);
        textField1.setLayoutY(15);
        textField1.setVisible(true);
        textField1.setPromptText("ali");
//        dialogPane.getChildren().add(textField);
        alert.getDialogPane().getChildren().add(textField1);
        System.out.println(dialogPane.getChildren());
        alert.showAndWait();
    }
}
