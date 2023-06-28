package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MessageGui{
    private final Pane pane = new Pane();
    private final Label textLabel;
    private final Image iv;
    public MessageGui(Message message) {
        textLabel = new Label(message.getText());
        textLabel.setMaxWidth(270);
        textLabel.setStyle("-fx-background-color: #9aa2f1;-fx-border-radius: 8;-fx-background-radius: 9;");
        pane.setMaxWidth(300);
        pane.setMinWidth(300);
        textLabel.setLayoutX(35);
        textLabel.setAlignment(Pos.CENTER_LEFT);
        iv = new Image("file:/home/morteza/Desktop/html/Painter/images/auth-github.png");//should be changed!
        Circle circle = new Circle();
        circle.setRadius(11);
        circle.setFill(new ImagePattern(iv));
        circle.relocate(8,8);
        pane.getChildren().addAll(textLabel,circle);

    }

    public Pane getPane() {
        return pane;
    }
}
