package view;

import ServerConnection.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Chat;
import model.Message;
import model.Messenger;
import model.generalenums.MessageEnum;

public class TestMessage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Message message = new Message("ali","this is only for test and we want to compare it telegram " +
                "messenger to test whether or not it is able to satisfy our need or not", MessageEnum.ROOM);
        Message secondMessage = new Message("ali","this is the second test",MessageEnum.ROOM);
        Chat chat = new Chat(null,MessageEnum.PRIVATE_CHAT);
//        MessageGui messageGui2 = new MessageGui(secondMessage,false);
//        MessageGui messageGui = new MessageGui(message,true);
//        VBox mainPane = new VBox();
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setMaxWidth(600);
//        scrollPane.setMaxWidth(600);
//        scrollPane.setContent(mainPane);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        mainPane.getChildren().add(messageGui.getPane());
//        mainPane.getChildren().add(messageGui2.getPane());
        primaryStage.setResizable(false);
        Messenger messenger = new Messenger(new User("a","a","a","as","slogan",0));
        Scene scene = new Scene(messenger.getPanepane());
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
