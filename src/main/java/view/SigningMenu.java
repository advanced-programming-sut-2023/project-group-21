package view;

import controller.FileController;
import controller.SignUpController;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import view.commands.CheckValidion;

import javax.swing.text.LabelView;
import java.io.BufferedReader;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigningMenu extends Application {
    Label usernameLabel = new Label("username");
    TextField usernameTextField;
    PasswordField passwordTextField;
    TextField passwordShow;
    TextField nicknameTextField;
    TextField emailTextField;
    Button backButton;
    Button saveButton;
    ToggleButton showButton = new ToggleButton("show/hide");
    private Label userNameError = new Label();
    private Pane mainPane;
    private SignUpController signUpController = new SignUpController();

    @Override
    public void start(Stage stage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/SigningMenu.fxml");
        mainPane = FXMLLoader.load(url);

        addNodes(mainPane);

        Scene scene = new Scene(mainPane);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("SignUp");
        stage.setScene(scene);
        stage.show();
    }

    private void addNodes(Pane mainPane) {
        //declaration
        Label usernameLabel = new Label("username");
        Label passwordLabel = new Label("password");
        Label nicknameLabel = new Label("nickname");
        Label emailLabel = new Label("email");
        usernameTextField = new TextField();
        passwordTextField = new PasswordField();
        passwordShow = new TextField();
        nicknameTextField = new TextField();
        emailTextField = new TextField();
        backButton = new Button("back");
        saveButton = new Button("save");

        //appearance
        showButton.setId("show-button");
        passwordTextField.setId("PassField");
        usernameLabel.relocate(420, 180);
        usernameTextField.relocate(420, 210);
        userNameError.relocate(630, 210);
        passwordLabel.relocate(420, 240);
        passwordTextField.relocate(420, 270);
        passwordShow.relocate(420, 270);
        showButton.relocate(630, 270);
        nicknameLabel.relocate(420, 300);
        nicknameTextField.relocate(420, 330);
        emailLabel.relocate(420, 360);
        emailTextField.relocate(420, 390);
        saveButton.relocate(420, 480);
        backButton.relocate(480, 480);
        userNameError.setId("error");

        //behaviour
        usernameTextField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //FileController.checkExistenceOfUserOrEmail(usernameTextField.getText(),true)
                if (!CheckValidion.check(usernameTextField.getText(), CheckValidion.CHECK_USERNAME) || false) {
                    if (!mainPane.getChildren().contains(userNameError)) {
                        updateUsernameError(userNameError, usernameTextField.getText());
                        mainPane.getChildren().add(userNameError);
                    }
                } else if (mainPane.getChildren().contains(userNameError)) {
                    mainPane.getChildren().remove(userNameError);
                }
            }
        });

        showButton.setOnMouseClicked(mouseEvent -> {
            if (showButton.isSelected()) {
                passwordShow.setText(passwordTextField.getText());
                mainPane.getChildren().add(passwordShow);
            } else {
                passwordTextField.setText(passwordShow.getText());
                mainPane.getChildren().remove(passwordShow);
            }
        });

        backButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                StartingMenu startingMenu = new StartingMenu();
                try {
                    startingMenu.start(StartingMenu.mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mainPane.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordTextField,
                nicknameLabel, nicknameTextField, backButton, saveButton,
                emailLabel, emailTextField, showButton);
    }


    private void updateUsernameError(Label label, String username) {
        //FileController.checkExistenceOfUserOrEmail(username,true)
        if (false)
            label.setText("The username exists");
        else
            label.setText("Invalid username");
    }


}
