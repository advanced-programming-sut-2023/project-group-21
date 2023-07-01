package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.Serializable;

public class MessageGui implements Serializable {
    private final Pane pane = new Pane();
    private final Label textLabel;
    private final Image iv;
    private final Message message;
    private final Label timeLabel;

    public Label getTextLabel() {
        return textLabel;
    }

    public Message getMessage() {
        return message;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public MessageGui(Message message, boolean state) {
        this.message = message;
        timeLabel = new Label(message.getTimeString());
        timeLabel.setFont(new Font(8));
        timeLabel.setOpacity(0.70);
        textLabel = new Label(message.getText());
        textLabel.setMaxWidth(540);
        textLabel.setMinWidth(540);
        textLabel.setStyle("-fx-background-color: #9aa2f1;-fx-border-radius: 8;-fx-background-radius: 9;");

        pane.setMaxWidth(600);
        pane.setMinWidth(600);
        pane.setPadding(new Insets(2));
        textLabel.setLayoutX(30);
        textLabel.setAlignment(Pos.CENTER_LEFT);
        iv = new Image("file:/home/morteza/Desktop/html/Painter/images/auth-github.png");//should be changed!
        Circle circle = new Circle();
        circle.setRadius(7);
        circle.setFill(new ImagePattern(iv));
        if (state)
            circle.relocate(11,2);
        else
            circle.relocate(575,2);
        timeLabel.relocate(35,15 * message.calculateLineNumber() + 5);
        pane.getChildren().addAll(textLabel,circle,timeLabel);
    }

    public void seeMessage(){
        System.out.println("message has been seen!");
    }

    public Pane getPane() {
        return pane;
    }

}
