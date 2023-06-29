package model;

import controller.OtherController;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.generalenums.MessageEnum;

import java.io.File;
import java.util.ArrayList;

public class Chat {
    private final User admin;
    private final ArrayList<User> allUsers = new ArrayList<>();
    private final ArrayList<Message> allMessages = new ArrayList<>();
    private final ScrollPane scrollPane = new ScrollPane();
    private TextField textField;
    private boolean isOnEdit = false;
    private Label editingLabel, editingTimeLabel;
    private static Pane staticMainPane2;
    private final MessageEnum messageEnum;
    VBox mainPane = new VBox();

    public Chat(User admin,MessageEnum messageEnum) {
        this.admin = admin;
        allUsers.add(admin);
        this.messageEnum = messageEnum;
        allMessages.add(new Message("messenger", "chat started!", messageEnum));
    }

    public MessageEnum getMessageEnum() {
        return messageEnum;
    }

    public Pane makeChatPane() {
        if (staticMainPane2 != null)
            return staticMainPane2;
        scrollPane.setContent(mainPane);
        scrollPane.setMaxWidth(600);
        scrollPane.setMaxWidth(600);
        scrollPane.setMinHeight(600);
        scrollPane.setMaxHeight(600);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for (Message allMessage : allMessages) {
            MessageGui messageGui = new MessageGui(allMessage, true);
            addContextMenu(messageGui.getTextLabel(), messageGui);
            mainPane.getChildren().add(messageGui.getPane());
        }
        Pane secondPane = makePane();
        Pane mainPane2 = new Pane();
        mainPane2.setPrefSize(600,720);
        mainPane2.getChildren().addAll(scrollPane, secondPane);
        staticMainPane2 = mainPane2;
        return mainPane2;//
    }

    public void addChat(Message message) {
        allMessages.add(message);
    }

    private Pane makePane() {
        Pane pane = new Pane();
        Circle circle = new Circle(20);
        circle.setFill(new ImagePattern(new Image("file:" + (new File("").getAbsolutePath()) +
                "/src/main/resources/images/star.png")));
        textField = new TextField();
        textField.setMaxSize(500, 70);
        textField.setMinSize(500, 70);
        textField.relocate(20, 20);
        pane.getChildren().addAll(textField, circle);
        circle.relocate(540, 40);
        pane.relocate(0, 600);
        circle.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (!isOnEdit) {
                    Message message = new Message("test", textField.getText(), MessageEnum.PRIVATE_CHAT);
                    addChat(message);
                    MessageGui messageGui = new MessageGui(message, true);
                    mainPane.getChildren().add(messageGui.getPane());
                    textField.setText("");
                    addContextMenu(messageGui.getTextLabel(), messageGui);
                } else {
                    if (editingLabel != null) {
                        editingLabel.setText(OtherController.makeAlignment(textField.getText(), 80));
                        isOnEdit = false;
                        editingTimeLabel.relocate(35, 15 * OtherController.calculateLineNumber(textField.getText()) + 5);
                        textField.setText("");
                    }
                }
            }
        });
        return pane;
    }

    private void addContextMenu(Label label, MessageGui messageGui) {
        ContextMenu contextMenu = new ContextMenu();
        final MenuItem deleteItem = new MenuItem("delete");
        deleteItem.setOnAction(actionEvent -> {
            allMessages.remove(messageGui.getMessage());
            mainPane.getChildren().remove(messageGui.getPane());
        });
        final MenuItem editItem = new MenuItem("edit");
        editItem.setOnAction(actionEvent -> {
            isOnEdit = true;
            editingLabel = messageGui.getTextLabel();
            editingTimeLabel = messageGui.getTimeLabel();
            textField.setText(messageGui.getMessage().getText());
        });
        final MenuItem copyItem = new MenuItem("copy");
        copyItem.setOnAction(actionEvent -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(label.getText());//send it to system clipboard!
            clipboard.setContent(content);
        });
        final MenuItem deleteForEveryone = new MenuItem("delete for every one");
        deleteForEveryone.setOnAction(actionEvent -> System.out.println("it did not handled!"));
        contextMenu.setOpacity(0.80);
        contextMenu.getItems().addAll(deleteItem, editItem,copyItem,deleteForEveryone);
        label.setContextMenu(contextMenu);
    }

}
