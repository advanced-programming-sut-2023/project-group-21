package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;

public class StartingMenu extends Application {
    public static Stage mainStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/StartingMenu.fxml");
        Pane mainPane = FXMLLoader.load(url);
        mainStage=stage;

        addButtons(mainPane);

        Scene scene=new Scene(mainPane);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Stronghold");
        stage.setScene(scene);
        stage.show();
    }

    private void addButtons(Pane pane){
        //declaration
        Button loginButton=new Button("Login");
        Button signUpButton=new Button("SignUp");
        Button quitButton=new Button("Quit");

        //placement
        quitButton.relocate(120,240);
        loginButton.relocate(120,120);
        signUpButton.relocate(120,180);

        //behaviour
        signUpButton.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()==MouseButton.PRIMARY){
                SigningMenu signingMenu=new SigningMenu();
                try {
                    signingMenu.start(mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        quitButton.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()== MouseButton.PRIMARY)
                Platform.exit();
        });

        pane.getChildren().addAll(signUpButton,loginButton,quitButton);
    }
}
