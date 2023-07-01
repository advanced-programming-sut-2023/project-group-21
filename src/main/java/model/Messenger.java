package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.generalenums.MessageEnum;

import java.io.File;
import java.util.ArrayList;

public class Messenger {
    private final static  String PROJECT_ADDRESS = (new File("")).getAbsolutePath();
    private final ScrollPane scrollPane = new ScrollPane();
    private final ArrayList<Chat> allChat;
    private final Pane mainPane = new Pane();
    private Pane firstPane = new Pane();
    private Pane panepane = new Pane();
    private Pane deletingChat;
    public Messenger(User user) {
        firstPane.setPrefSize(600,720);
        panepane.setPrefSize(1080,720);
        mainPane.relocate(600,0);
        allChat = user.getChats();
        scrollPane.setContent(mainPane);
        scrollPane.setMaxWidth(380);
        scrollPane.setMaxWidth(380);
        scrollPane.setMinHeight(720);
        scrollPane.setMaxHeight(720);
        for (Chat chat:allChat){
            mainPane.getChildren().add(makePane(chat));
        }
        deletingChat = allChat.get(0).makeChatPane();
        panepane.getChildren().addAll(allChat.get(0).makeChatPane(),mainPane);
    }

    private Pane makePane(Chat chat){
        Pane tempPane = new Pane();
        tempPane.setPrefSize(380,60);
        Circle circle = new Circle(20);
        circle.setFill(new ImagePattern(new Image("file:" + PROJECT_ADDRESS + "/src/main/resources/images/eye.png")));
        circle.relocate(25,25);
        tempPane.setOnMouseClicked(mouseEvent -> {
            doAction(tempPane);
        });
        tempPane.setStyle("-fx-background-color: red");
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addMember = new MenuItem();
        addMember.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAddUserStage(chat);
            }
        });
        contextMenu.getItems().addAll(addMember);
        tempPane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent contextMenuEvent) {
                showAddUserStage(chat);
            }
        });
        return tempPane;
    }

    private void showAddUserStage(Chat chat){
        if (chat.getMessageEnum() == MessageEnum.PRIVATE_CHAT){
            System.out.println("it is impossible to do that!");
        }else {
            Stage stage = new Stage();
            TextField usernameTextField = new TextField();
            usernameTextField.relocate(150,100);
            Button button = new Button("add");
            button.relocate(170,300);
            button.setOnMouseClicked(mouseEvent -> {
                stage.close();
            });
            Pane pane = new Pane(usernameTextField,button);
            pane.setMaxSize(400,400);
            pane.setMinSize(400,400);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        }
    }

    private void doAction(Pane pane){
        int indexNumber = mainPane.getChildren().indexOf(pane);
        panepane.getChildren().remove(deletingChat);
        deletingChat = allChat.get(indexNumber).makeChatPane();
        panepane.getChildren().add(0,deletingChat);
    }

    public Pane getPanepane(){
        return panepane;
    }
}
