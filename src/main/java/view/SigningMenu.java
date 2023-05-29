package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.net.URL;

public class SigningMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/SigningMenu.fxml");
        Pane mainPane = FXMLLoader.load(url);

        addNodes(mainPane);

        Scene scene=new Scene(mainPane);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("SignUp");
        stage.setScene(scene);
        stage.show();
    }

    private void addNodes(Pane mainPane) {
        //declaration
        Label usernameLabel=new Label("username");
        Label passwordLabel=new Label("password");
        Label nicknameLabel=new Label("nickname");
        Label emailLabel=new Label("email");
        TextField usernameTextField=new TextField();
        TextField passwordTextField=new TextField();
        TextField nicknameTextField=new TextField();
        TextField emailTextField=new TextField();
        Button backButton=new Button("back");
        Button saveButton=new Button("save");

        //placement
        usernameLabel.relocate(420,180);
        usernameTextField.relocate(420,210);
        passwordLabel.relocate(420,240);
        passwordTextField.relocate(420,270);
        nicknameLabel.relocate(420,300);
        nicknameTextField.relocate(420,330);
        emailLabel.relocate(420,360);
        emailTextField.relocate(420,390);
        saveButton.relocate(420,480);
        backButton.relocate(480,480);

        //behaviour

        backButton.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()== MouseButton.PRIMARY) {
                StartingMenu startingMenu = new StartingMenu();
                try {
                    startingMenu.start(StartingMenu.mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mainPane.getChildren().addAll(usernameLabel,usernameTextField,passwordLabel,passwordTextField,
                                      nicknameLabel,nicknameTextField,backButton,saveButton);
    }
}
