package view;

import controller.FileController;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Game;
import model.User;
import view.commands.CheckValidion;

import java.net.URL;

public class LoggingMenu extends Application {
    private Pane mainPane;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button backButton;
    private Button saveButton;
    private ToggleButton showButton = new ToggleButton("show/hide");
    private TextField passwordShow;
    private Hyperlink forgotPassword;
    private StartingMenu startingMenu;

    public void setStartingMenu(StartingMenu startingMenu) {
        this.startingMenu = startingMenu;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/LoggingMenu.fxml");
        mainPane = FXMLLoader.load(url);

        addNodes();

        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
    }

    private void addNodes() {
        //declaration
        Label loginMenuLabel = new Label("Login Menu");
        Label usernameLabel = new Label("username:");
        usernameField = new TextField();
        Label passwordLabel = new Label("password:");
        passwordField = new PasswordField();
        forgotPassword = new Hyperlink();
        passwordShow = new TextField();
        backButton = new Button("back");
        saveButton = new Button("save");
        Label errorLabel = new Label("username field is empty");

        //appearance
        usernameLabel.relocate(420, 215);
        usernameField.relocate(420, 240);
        passwordLabel.relocate(420, 275);
        passwordField.relocate(420, 300);
        passwordShow.relocate(420, 300);
        saveButton.relocate(420, 420);
        backButton.relocate(480, 420);
        showButton.relocate(630, 300);
        loginMenuLabel.relocate(450, 40);
        showButton.setId("show-button");
        passwordField.setId("PassField");
        loginMenuLabel.setId("title");
        forgotPassword.relocate(420, 340);
        forgotPassword.setText("forgot password");
        errorLabel.setId("error");
        errorLabel.relocate(420, 500);


        //behaviour
        saveButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (FileController.getUserByUsername(usernameField.getText()) != null) {
                    User user;
                    user = FileController.getUserByUsername(usernameField.getText());
                    if (user != null && FileController.getUserByUsername(usernameField.getText()).getPassword().
                            equals(passwordField.getText())) {
                        MainMenu mainMenu = new MainMenu();
                        mainMenu.setUser(user);
                        mainMenu.setLoggingMenu(this);
                        mainMenu.start(StartingMenu.mainStage);
                    } else {
                        errorLabel.setText("incorrect password");
                        if (!mainPane.getChildren().contains(errorLabel))
                            mainPane.getChildren().add(errorLabel);
                    }
                }
            }
        });
        showButton.setOnMouseClicked(mouseEvent -> {
            if (showButton.isSelected()) {
                passwordShow.setText(passwordField.getText());
                mainPane.getChildren().add(passwordShow);
            } else {
                passwordField.setText(passwordShow.getText());
                mainPane.getChildren().remove(passwordShow);
            }
        });
        backButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                try {
                    startingMenu.start(StartingMenu.mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        forgotPassword.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && FileController.getSecurityQuestion(usernameField.getText()) != 0) {
                Pane pane = new Pane();
                Scene scene = new Scene(pane);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
                stage.show();
                stage.setWidth(450);
                stage.setHeight(350);
                TextField answer = new TextField();
                Button saveAnswer = new Button("save");
                saveAnswer.relocate(230, 150);
                answer.relocate(40, 150);
                pane.getChildren().addAll(saveAnswer, answer);
                if (usernameField.getText().isEmpty()) {
                    if (!mainPane.getChildren().contains(errorLabel))
                        mainPane.getChildren().add(errorLabel);
                } else {
                    Label question = new Label();
                    question.setText(Game.SECURITY_QUESTION[FileController.getSecurityQuestion(usernameField.getText())]);
                    question.relocate(40, 80);
                    pane.getChildren().add(question);
                }
                saveAnswer.setOnMouseClicked(mouseEvent1 -> {
                    if (mouseEvent1.getButton() == MouseButton.PRIMARY) {
                        // check answer if(answer.getText().equals(FileController.getUserByUsername(usernameField.getText())))
                        newPasswordScene(stage);
                    }
                });
            }
        });

        mainPane.getChildren().

                addAll(usernameLabel, usernameField, passwordLabel, passwordField,
                        backButton, saveButton, showButton, loginMenuLabel, forgotPassword);

    }

    private void newPasswordScene(Stage stage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        TextField passwordField = new TextField();
        Button saveButton = new Button("save");
        saveButton.relocate(100, 150);
        passwordField.relocate(100, 70);
        pane.getChildren().addAll(saveButton, passwordField);
        Label weakPasswordError = new Label("password is weak!");
        weakPasswordError.relocate(300, 215);
        weakPasswordError.setId("error");

        saveButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                passwordField.textProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        if (!CheckValidion.check(passwordField.getText(), CheckValidion.CHECK_PASSWORD)) {
                            if (!pane.getChildren().contains(weakPasswordError)) {
                                pane.getChildren().add(weakPasswordError);
                            }
                        } else if (pane.getChildren().contains(weakPasswordError)) {
                            pane.getChildren().remove(weakPasswordError);
                            FileController.getUserByUsername(usernameField.getText()).setPassword(passwordField.getText());
                            stage.close();
                        }
                    }
                });
            }
        });


    }

}
