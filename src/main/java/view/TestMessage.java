package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Message;
import model.MessageGui;
import model.generalenums.MessageEnum;

public class TestMessage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Message message = new Message("ali","this is only for test and we want to compare it telegram\n " +
                "messenger to test whether or not it is able to satisfy our need or not", MessageEnum.ROOM);
        MessageGui messageGui = new MessageGui(message);
        Scene scene = new Scene(messageGui.getPane());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
