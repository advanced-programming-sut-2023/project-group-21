package view;

import ServerConnection.LoginMessage;
import controller.FileController;
import controller.OtherController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ServerConnection.User;
import javafx.util.Duration;
import view.commands.CheckValidion;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Profile extends Application {
    private Pane mainPane;

    private User user;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/Profile.fxml");
        mainPane = FXMLLoader.load(url);

        addTitle();
        addUsername();
        addSlogan();
        addNickname();
        addEmail();
        addPassword();
        addLeaderboard();
        addBackButton();
        addAvatar(user.getPictureName());


        Scene scene = new Scene(mainPane);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Stronghold");
        stage.setScene(scene);
    }

    private void addLeaderboard() {
        Button leaderboardButton = new Button("leaderBoard");

        leaderboardButton.setOnMouseClicked(mouseEvent -> {
            Stage boardStage=new Stage();


            TableView table = new TableView<User>();


            TableColumn nameColumn = new TableColumn<User, String>("userName");
            nameColumn.cellFactoryProperty();
            nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
            TableColumn scoreColumn = new TableColumn<User, Integer>("score");
            scoreColumn.cellFactoryProperty();
            scoreColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("score"));
            TableColumn timeColumn = new TableColumn<User, Boolean>("isOnline");
            timeColumn.cellFactoryProperty();
            timeColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("isOnline"));
            table.getColumns().addAll(nameColumn, scoreColumn, timeColumn);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//            FileController.getAllUsers().sort(User::compareTo);
            ObservableList<User> users= FXCollections.observableArrayList(FileController.getAllUsers());

            table.setItems(users);
            scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
            table.getSortOrder().add(scoreColumn);
            table.prefHeightProperty().bind(boardStage.heightProperty());
            table.prefWidthProperty().bind(boardStage.widthProperty());

            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Stage newStage = new Stage();
                    newStage.setWidth(200);
                    newStage.setHeight(200);
                    User selectedItem = (User) newSelection;
//                    try {
//                        StartingMenu.getDOut().writeObject(new LoginMessage(((User) newSelection).getUserName(), "s")););
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
                    Circle circle = new Circle(70);
                    String path = "/images/avatar/" + selectedItem.getPictureName();
                    Image image = new Image(Profile.class.getResource(path).toExternalForm());
                    circle.setStroke(Color.BLACK);
                    circle.setFill(new ImagePattern(image));
                    circle.relocate(0,0);
                    Pane pane=new Pane(circle);
                    pane.setPrefWidth(200);
                    pane.setPrefHeight(200);
                    Scene newScene = new Scene(pane);
                    newStage.setScene(newScene);

                    // Show the new stage
                    newStage.show();
                }
            });



            Timeline timeline=new Timeline(new KeyFrame(Duration.seconds(10), actionEvent -> {
                update(table,scoreColumn);
            }));
            timeline.setCycleCount(-1);
            timeline.play();

            Pane boardPane=new Pane(table);
            Scene boardScene=new Scene(boardPane);
            boardStage.setScene(boardScene);
            boardStage.setWidth(400);
            boardStage.setHeight(300);
            boardStage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
            boardStage.setTitle("Leaderboard");
            boardStage.show();
        });

        leaderboardButton.relocate(380, 400);
        mainPane.getChildren().add(leaderboardButton);
    }

    private void update(TableView table, TableColumn scoreColumn) {
        table.getItems().clear();
        ObservableList<User> users= FXCollections.observableArrayList(FileController.getAllUsers());
        table.getItems().addAll(users);
        table.sort();
    }

    private void addSlogan() {
        Label sloganLabel = new Label(user.getSlogan());
        sloganLabel.relocate(820, 220);
        sloganLabel.setFont(Font.font(50));
        mainPane.getChildren().add(sloganLabel);
        sloganLabel.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                changeSlogan();
            }
        });
    }

    private void changeSlogan() {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();

        Label newSlogan = new Label("new slogan:");
        newSlogan.relocate(30, 30);
        TextField sloganField = new TextField();
        sloganField.relocate(30, 60);
        Button saveButton = new Button("save");
        saveButton.relocate(30, 130);

        saveButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                stage.close();
                user.setSlogan(sloganField.getText());
                if(sloganField.getText().isEmpty())
                    user.setSlogan("slogan is empty");
                Profile profile = new Profile();
                profile.setUser(user);
                try {
                    profile.start(StartingMenu.mainStage);
                    successPopUp("slogan");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });



        pane.getChildren().addAll(sloganField, newSlogan, saveButton);
        stage.setScene(scene);
        stage.setHeight(220);
        stage.setWidth(350);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Slogan Change");
        stage.show();
    }

    private void addBackButton() {
        Button backButton = new Button("back");
        backButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                MainMenu mainMenu = new MainMenu();
                mainMenu.setUser(this.user);
                try {
                    mainMenu.start(StartingMenu.mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        backButton.relocate(320, 400);
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
                try {
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
                    } else if (!pane.getChildren().contains(saveError))
                        pane.getChildren().add(saveError);
                } catch (IOException | InterruptedException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
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
                changePassword();
            }
        });
    }

    private void changePassword() {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();

        Label oldPassword = new Label("old password:");
        Label newPassword = new Label("new password:");
        TextArea captchaArea = new TextArea(OtherController.generateCaptcha());
        PasswordField oldPasswordField = new PasswordField();
        PasswordField newPasswordField = new PasswordField();
        TextField captchaField=new TextField();
        Button saveButton = new Button("save");
        oldPassword.relocate(40, 30);
        oldPasswordField.relocate(40, 60);
        newPassword.relocate(40, 90);
        newPasswordField.relocate(40, 120);
        captchaArea.relocate(50, 180);
        captchaArea.minWidth(400);
        captchaArea.minHeight(140);
        saveButton.relocate(40, 450);
        captchaField.relocate(280,370);


        Label error = new Label("weakPassword");
        error.setTextFill(Color.RED);
        error.setFont(Font.font(12));
        error.relocate(200, 125);
        newPasswordField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //if (!CheckValidion.check(newPasswordField.getText(), CheckValidion.CHECK_PASSWORD)) {
                    if (!pane.getChildren().contains(error))
                        pane.getChildren().add(error);
                //}
                else if (pane.getChildren().contains(error))
                    pane.getChildren().remove(error);
            }
        });

        Label saveError = new Label("weakPassword");
        saveError.setTextFill(Color.RED);
        saveError.setFont(Font.font(12));
        saveError.relocate(140, 450);

        saveButton.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()==MouseButton.PRIMARY){
                if(!oldPasswordField.getText().equals(user.getPassword())) {
                    saveError.setText("old password is incorrect");
                    if(!pane.getChildren().contains(saveError))
                        pane.getChildren().add(saveError);
                }
                if(!OtherController.checkCaptcha(captchaField.getText())) {
                    saveError.setText("captcha is incorrect");
                    if(!pane.getChildren().contains(saveError))
                        pane.getChildren().add(saveError);
                }

                if(OtherController.checkCaptcha(captchaField.getText())&&oldPasswordField.getText().equals(user.getPassword())&&
                        CheckValidion.check(newPasswordField.getText(), CheckValidion.CHECK_PASSWORD)){
                    user.setPassword(newPasswordField.getText());
                    stage.close();
                    Profile profile = new Profile();
                    profile.setUser(user);
                    try {
                        profile.start(StartingMenu.mainStage);
                        successPopUp("password");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        pane.getChildren().addAll(captchaArea,captchaField,oldPasswordField,newPasswordField,newPassword,oldPassword,saveButton);
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(700);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Password Change");
        stage.show();
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
                try {
                    if (!CheckValidion.check(usernameTextField.getText(), CheckValidion.CHECK_USERNAME) || FileController.checkExistenceOfUserOrEmail(usernameTextField.getText(), true)) {
                        updateUsernameError(userNameError, usernameTextField.getText());
                        if (!pane.getChildren().contains(userNameError)) {
                            pane.getChildren().add(userNameError);
                        }
                    } else if (pane.getChildren().contains(userNameError)) {
                        pane.getChildren().remove(userNameError);
                    }
                } catch (IOException | InterruptedException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        saveButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                try {
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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
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
        String path = "/images/avatar/" + picture;
        Image image = new Image(Profile.class.getResource(path).toExternalForm());
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
        Pane pane = new Pane();
        Label changedLabel = new Label(factor + " changed successfully");
        changedLabel.relocate(15, 20);
        changedLabel.setFont(Font.font(18));
        pane.getChildren().add(changedLabel);


        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setHeight(200);
        stage.setWidth(260);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("success");
        stage.show();
    }

    private void updateUsernameError(Label label, String username) throws IOException, InterruptedException, ClassNotFoundException {
        if (FileController.checkExistenceOfUserOrEmail(username, true))
            label.setText("The username exists");
        else
            label.setText("Invalid username");
    }

}
