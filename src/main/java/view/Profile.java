package view;

import controller.FileController;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import view.commands.CheckValidion;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;

public class Profile extends Application {
    private Pane mainPane;

    private User user;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/Profile.fxml");
        mainPane = FXMLLoader.load(url);

        addTitle();
        addUsername();
        //handle password change
        addNickname();
        addEmail();
        addPassword();
        addBackButton();
        addAvatar(user.getPictureName());

        Scene scene = new Scene(mainPane);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Stronghold");
        stage.setScene(scene);
    }

    private void addBackButton() {
        Button backButton=new Button("back");
        backButton.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()==MouseButton.PRIMARY){
                StartingMenu startingMenu=new StartingMenu();
                try {
                    startingMenu.start(StartingMenu.mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        backButton.relocate(320,400);
        mainPane.getChildren().add(backButton);
    }

    private void addEmail() {
        Label emailLabel = new Label("email:   " + user.getEmail());
        emailLabel.relocate(320, 220);
        mainPane.getChildren().add(emailLabel);
        emailLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                changeEmail();
            }
        });
    }

    private void changeEmail() {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();

        Label newEmailLabel = new Label("new email:");
        newEmailLabel.relocate(30, 30);
        TextField emailField = new TextField();
        emailField.relocate(30, 60);
        Button saveButton = new Button("save");
        saveButton.relocate(30, 130);
        Label saveError = new Label("existed email");
        saveError.setTextFill(Color.RED);
        saveError.setFont(Font.font(10));
        saveError.relocate(180, 65);


        saveButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (!FileController.checkExistenceOfUserOrEmail(emailField.getText(), false)) {
                    saveError.setText("existed email");
                    stage.close();
                    user.setEmail(emailField.getText());
                    Profile profile = new Profile();
                    profile.setUser(user);
                    try {
                        profile.start(StartingMenu.mainStage);
                        successPopUp("email");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(!pane.getChildren().contains(saveError))
                    pane.getChildren().add(saveError);
            }
        });

        pane.getChildren().addAll(emailField, newEmailLabel, saveButton);
        stage.setScene(scene);
        stage.setHeight(220);
        stage.setWidth(350);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Email Change");
        stage.show();
    }

    private void addNickname() {
        Label nicknameLabel = new Label("nickname:   " + user.getNickName());
        nicknameLabel.relocate(320, 180);
        mainPane.getChildren().add(nicknameLabel);
        nicknameLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                changeNickname();
            }
        });
    }

    private void changeNickname() {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();

        Label newNickname = new Label("new nickname:");
        newNickname.relocate(30, 30);
        TextField nicknameField = new TextField();
        nicknameField.relocate(30, 60);
        Button saveButton = new Button("save");
        saveButton.relocate(30, 130);

        saveButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                stage.close();
                user.setNickName(nicknameField.getText());
                Profile profile = new Profile();
                profile.setUser(user);
                try {
                    profile.start(StartingMenu.mainStage);
                    successPopUp("nickname");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pane.getChildren().addAll(nicknameField, newNickname, saveButton);
        stage.setScene(scene);
        stage.setHeight(220);
        stage.setWidth(350);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Nickname Change");
        stage.show();
    }

    private void addPassword() {
        Label passwordLabel = new Label("password:   " + user.getPassword());
        passwordLabel.relocate(320, 140);
        mainPane.getChildren().add(passwordLabel);
        passwordLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                //changePassword();
            }
        });
    }

    private void addTitle() {
        Label title = new Label("Profile Menu");
        title.relocate(500, 20);
        title.setId("title");
        mainPane.getChildren().add(title);
    }

    private void addUsername() {
        Label usernameLabel = new Label("username:   " + user.getUserName());
        usernameLabel.relocate(320, 100);
        mainPane.getChildren().add(usernameLabel);
        usernameLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                changeUsername();
            }
        });
    }

    private void changeUsername() {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();

        Label newUsername = new Label("new username:");
        newUsername.relocate(30, 30);
        TextField usernameTextField = new TextField();
        usernameTextField.relocate(30, 60);
        Button saveButton = new Button("save");
        saveButton.relocate(30, 130);

        Label userNameError = new Label("invalid username");
        userNameError.setTextFill(Color.RED);
        userNameError.setFont(Font.font(10));
        userNameError.relocate(180, 65);
        usernameTextField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (!CheckValidion.check(usernameTextField.getText(), CheckValidion.CHECK_USERNAME) || FileController.checkExistenceOfUserOrEmail(usernameTextField.getText(), true)) {
                    updateUsernameError(userNameError, usernameTextField.getText());
                    if (!pane.getChildren().contains(userNameError)) {
                        pane.getChildren().add(userNameError);
                    }
                } else if (pane.getChildren().contains(userNameError)) {
                    pane.getChildren().remove(userNameError);
                }
            }
        });

        saveButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (CheckValidion.check(usernameTextField.getText(), CheckValidion.CHECK_USERNAME) && !FileController.checkExistenceOfUserOrEmail(usernameTextField.getText(), true)) {
                    user.setUserName(usernameTextField.getText());
                    stage.close();
                    Profile profile = new Profile();
                    profile.setUser(user);
                    try {
                        profile.start(StartingMenu.mainStage);
                        successPopUp("username");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        pane.getChildren().addAll(usernameTextField, newUsername, saveButton);
        stage.setScene(scene);
        stage.setHeight(220);
        stage.setWidth(350);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Username Change");
        stage.show();
    }

    private void addAvatar(String picture) {
        Circle circle = new Circle(70);
        Image image = new Image((new File("").getAbsolutePath()) +
                "/src/main/resources/images/avatar/" + picture);
        circle.setStroke(Color.BLACK);
        circle.setFill(new ImagePattern(image));
        circle.relocate(900, 80);


        circle.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                changeAvatar();
            }
        });

        mainPane.getChildren().add(circle);
    }

    private void changeAvatar() {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();

        Circle circle1 = new Circle(40);
        Image image = new Image(StartMenu.class.getResourceAsStream("/images/avatar/pic1.jpg"));
        circle1.setStroke(Color.BLACK);
        circle1.setFill(new ImagePattern(image));
        circle1.relocate(20, 40);
        circle1.setOnMouseClicked(mouseEvent -> {
            user.setPictureName("pic1.jpg");
            stage.close();
            successPopUp("avatar");
            addAvatar("pic1.jpg");
        });

        Circle circle2 = new Circle(40);
        Image image2 = new Image(StartMenu.class.getResourceAsStream("/images/avatar/pic2.png"));
        circle2.setStroke(Color.BLACK);
        circle2.setFill(new ImagePattern(image2));
        circle2.relocate(130, 40);
        circle2.setOnMouseClicked(mouseEvent -> {
            user.setPictureName("pic2.png");
            stage.close();
            successPopUp("avatar");
            addAvatar("pic2.png");
        });

        Circle circle3 = new Circle(40);
        Image image3 = new Image(StartMenu.class.getResourceAsStream("/images/avatar/pic3.jpg"));
        circle3.setStroke(Color.BLACK);
        circle3.setFill(new ImagePattern(image3));
        circle3.relocate(240, 40);
        circle3.setOnMouseClicked(mouseEvent -> {
            user.setPictureName("pic3.jpg");
            stage.close();
            successPopUp("avatar");
            addAvatar("pic3.jpg");
        });

        Button selectButton = new Button("Select");
        selectButton.relocate(150, 140);
        selectButton.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg");

            fileChooser.setTitle("Choose Profile");
            fileChooser.getExtensionFilters().add(extensionFilter);
            fileChooser.setInitialDirectory(new File((new File("").getAbsolutePath()) +
                    "/src/main/resources/images/avatar"));

            File selectedAvatar = fileChooser.showOpenDialog(stage);

            if (selectedAvatar != null) {
                try {
                    Files.copy(selectedAvatar.toPath(), new File((new File("").getAbsolutePath()) +
                            "/src/main/resources/images/avatar/" + selectedAvatar.getName()).toPath());
                    user.setPictureName(selectedAvatar.getName());
                    stage.close();
                    Profile profile = new Profile();
                    profile.setUser(user);
                    profile.start(StartingMenu.mainStage);
                    successPopUp("avatar");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pane.getChildren().addAll(circle1, circle2, circle3, selectButton);


        stage.setScene(scene);
        stage.setHeight(220);
        stage.setWidth(350);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Avatar");
        stage.show();
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void successPopUp(String factor) {
        Pane pane=new Pane();
        Label changedLabel=new Label(factor+" changed successfully");
        changedLabel.relocate(15,20);
        changedLabel.setFont(Font.font(18));
        pane.getChildren().add(changedLabel);


        Scene scene=new Scene(pane);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.setHeight(200);
        stage.setWidth(260);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("success");
        stage.show();
    }

    private void updateUsernameError(Label label, String username) {
        if (FileController.checkExistenceOfUserOrEmail(username, true))
            label.setText("The username exists");
        else
            label.setText("Invalid username");
    }

}
