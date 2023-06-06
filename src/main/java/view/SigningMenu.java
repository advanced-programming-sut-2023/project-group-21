package view;

import controller.FileController;
import controller.OtherController;
import controller.SignUpController;
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
import view.commands.CheckValidion;
import view.message.SignUpMessages;


import java.net.URL;
import java.util.ArrayList;

import static view.message.SignUpMessages.PRINT_NOTHING;

public class SigningMenu extends Application {
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
    private CheckBox needSlogan;
    private SignUpController signUpController = new SignUpController();
    private Button randomPasswordGenerator;
    private TextField sloganTextField;
    private Button sloganRandom;
    private Button chooseSloganButton;
    private PasswordField passwordConfirmTextField;
    private StartingMenu startingMenu;

    public void setStartingMenu(StartingMenu startingMenu) {
        this.startingMenu = startingMenu;
    }

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
        Label signUpLabel = new Label("Sign Up Menu");
        Label usernameLabel = new Label("username");
        Label passwordLabel = new Label("password");
        Label passwordConfirmLabel = new Label("confirm");
        Label nicknameLabel = new Label("nickname");
        Label emailLabel = new Label("email");
        Label sloganLabel = new Label("slogan");
        Label weakPasswordError = new Label("password is weak!");
        Label saveError = new Label();
        usernameTextField = new TextField();
        passwordTextField = new PasswordField();
        passwordConfirmTextField = new PasswordField();
        passwordShow = new TextField();
        nicknameTextField = new TextField();
        emailTextField = new TextField();
        sloganTextField = new TextField();
        backButton = new Button("back");
        saveButton = new Button("save");
        randomPasswordGenerator = new Button("random");
        needSlogan = new CheckBox("slogan");
        sloganRandom = new Button("random");
        chooseSloganButton = new Button("choose");

        //appearance
        showButton.setId("show-button");
        passwordTextField.setId("PassField");
        passwordConfirmTextField.setId("PassField");
        signUpLabel.relocate(450, 40);
        usernameLabel.relocate(420, 124);
        usernameTextField.relocate(420, 150);
        userNameError.relocate(300, 155);
        passwordLabel.relocate(420, 184);
        passwordTextField.relocate(420, 210);
        passwordShow.relocate(420, 210);
        randomPasswordGenerator.relocate(710, 210);
        weakPasswordError.relocate(300, 215);
        passwordConfirmLabel.relocate(420, 244);
        passwordConfirmTextField.relocate(420, 270);
        showButton.relocate(630, 210);
        nicknameLabel.relocate(420, 304);
        nicknameTextField.relocate(420, 330);
        emailLabel.relocate(420, 364);
        emailTextField.relocate(420, 390);
        sloganLabel.relocate(420, 420);
        sloganTextField.relocate(420, 450);
        chooseSloganButton.relocate(780, 450);
        needSlogan.relocate(630, 450);
        sloganRandom.relocate(710, 450);
        saveButton.relocate(420, 510);
        backButton.relocate(480, 510);
        saveError.relocate(420, 540);
        userNameError.setId("error");
        weakPasswordError.setId("error");
        saveError.setId("error");
        signUpLabel.setId("title");


        //behaviour
        chooseSloganButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Pane pickPane = new Pane();
                Scene scene = new Scene(pickPane);
                Stage pickStage = new Stage();
                pickStage.setScene(scene);
                pickStage.show();
                pickStage.setWidth(450);
                pickStage.setHeight(350);
                ToggleGroup toggleGroup = new ToggleGroup();
                ArrayList<RadioButton> radioButtons = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    radioButtons.add(new RadioButton());
                    radioButtons.get(i).setToggleGroup(toggleGroup);
                    radioButtons.get(i).relocate(20, 20 + i * 24);
                    Label label = new Label(Game.SLOGANS.get(i));
                    label.relocate(40, 20 + i * 24);
                    pickPane.getChildren().addAll(label, radioButtons.get(i));
                }
                Button closeButton = new Button("close");
                Button chooseButton = new Button("choose");
                closeButton.relocate(100, 260);
                chooseButton.relocate(40, 260);
                pickPane.getChildren().addAll(closeButton, chooseButton);
                chooseButton.setOnMouseClicked(mouseEvent2 -> {
                    if (mouseEvent2.getButton() == MouseButton.PRIMARY) {
                        if (toggleGroup.getSelectedToggle() != null) {
                            int index = radioButtons.indexOf((RadioButton) toggleGroup.getSelectedToggle());
                            sloganTextField.setText(Game.SLOGANS.get(index));
                        }
                    }
                });
                closeButton.setOnMouseClicked(mouseEvent1 -> {
                    if (mouseEvent1.getButton() == MouseButton.PRIMARY) {
                        pickStage.close();
                    }
                });
            }
        });
        needSlogan.selectedProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (mainPane.getChildren().contains(sloganLabel)) {
                    mainPane.getChildren().removeAll(sloganLabel, sloganTextField, sloganRandom, chooseSloganButton);
                } else
                    mainPane.getChildren().addAll(sloganLabel, sloganTextField, sloganRandom, chooseSloganButton);
            }
        });

        sloganRandom.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Pane sloganPane = new Pane();
                sloganPane.setPrefWidth(400);
                sloganPane.setPrefHeight(200);
                String newSlogan = signUpController.giveRandomSlogan();
                Label createdSlogan = new Label(newSlogan);
                createdSlogan.relocate(20, 40);
                Button closeButton = new Button("close");
                Button chooseButton = new Button("choose");
                closeButton.relocate(100, 100);
                chooseButton.relocate(40, 100);
                sloganPane.getChildren().addAll(createdSlogan, closeButton, chooseButton);
                Stage showRandomSloganStage = new Stage();
                showRandomSloganStage.setScene(new Scene(sloganPane));
                chooseButton.setOnMouseClicked(mouseEvent2 -> {
                    if (mouseEvent2.getButton() == MouseButton.PRIMARY)
                        sloganTextField.setText(newSlogan);
                });
                closeButton.setOnMouseClicked(mouseEvent1 -> {
                    if (mouseEvent1.getButton() == MouseButton.PRIMARY) {
                        showRandomSloganStage.close();
                    }
                });
                showRandomSloganStage.show();
            }
        });

        randomPasswordGenerator.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Pane passwordPane = new Pane();
                passwordPane.setPrefWidth(200);
                passwordPane.setPrefHeight(200);
                String newPassword = signUpController.generateRandomPassword();
                Label createdPassword = new Label(newPassword);
                createdPassword.relocate(40, 40);
                Button closeButton = new Button("close");
                Button chooseButton = new Button("choose");
                closeButton.relocate(100, 100);
                chooseButton.relocate(40, 100);
                passwordPane.getChildren().addAll(createdPassword, closeButton, chooseButton);
                Stage showRandomPasswordStage = new Stage();
                showRandomPasswordStage.setScene(new Scene(passwordPane));
                chooseButton.setOnMouseClicked(mouseEvent2 -> {
                    if (mouseEvent2.getButton() == MouseButton.PRIMARY)
                        passwordTextField.setText(newPassword);
                });
                closeButton.setOnMouseClicked(mouseEvent1 -> {
                    if (mouseEvent1.getButton() == MouseButton.PRIMARY) {
                        showRandomPasswordStage.close();
                    }
                });
                showRandomPasswordStage.show();
            }
        });
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
        passwordTextField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (!CheckValidion.check(passwordTextField.getText(), CheckValidion.CHECK_PASSWORD)) {
                    if (!mainPane.getChildren().contains(weakPasswordError)) {
                        mainPane.getChildren().add(weakPasswordError);
                    }
                } else if (mainPane.getChildren().contains(weakPasswordError)) {
                    mainPane.getChildren().remove(weakPasswordError);
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
                try {
                    startingMenu.start(StartingMenu.mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        saveButton.setOnMouseClicked(mouseEvent -> {
            if (mainPane.getChildren().contains(passwordShow))
                passwordTextField.setText(passwordShow.getText());

            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                SignUpMessages message =
                        signUpController.checkValidationFormat(emailTextField.getText(), usernameTextField.getText(),
                                passwordTextField.getText(), passwordConfirmTextField.getText());
                saveError.setText(message.toString());

                if (mainPane.getChildren().contains(saveError))
                    mainPane.getChildren().remove(saveError);
                if (message.equals(PRINT_NOTHING)) {
                    if (!mainPane.getChildren().contains(sloganTextField)) {
                        sloganTextField.setText(signUpController.giveRandomSlogan());
                    }
                    if (mainPane.getChildren().contains(sloganTextField) && sloganTextField.getText().isEmpty())
                        saveError.setText("slogan is empty");
                    else
                        createQuestionScene();
                }
            }
            if (!mainPane.getChildren().contains(saveError))
                mainPane.getChildren().add(saveError);

        });


        mainPane.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel, passwordTextField,
                nicknameLabel, nicknameTextField, backButton, saveButton, signUpLabel, needSlogan, passwordConfirmLabel,
                emailLabel, emailTextField, showButton, randomPasswordGenerator, passwordConfirmTextField);
    }

    private void createQuestionScene() {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.show();
        stage.setWidth(450);
        stage.setHeight(350);
        Label answerLabel = new Label("answer:");
        TextField answerTextField = new TextField();
        Button saveAnswerButton = new Button("save");
        Label error = new Label("select a question and answer");
        error.relocate(120, 270);
        error.setId("error");
        answerTextField.relocate(50, 230);
        answerLabel.relocate(50, 200);
        saveAnswerButton.relocate(50, 270);
        final int[] selectedQuestionNumber = {0};

        //needs repair
        //save question and answer
        RadioButton radioButton1 = new RadioButton(Game.SECURITY_QUESTION[0]);
        RadioButton radioButton2 = new RadioButton(Game.SECURITY_QUESTION[1]);
        RadioButton radioButton3 = new RadioButton(Game.SECURITY_QUESTION[2]);
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton1.relocate(20, 40);
        radioButton2.relocate(20, 80);
        radioButton3.relocate(20, 120);
        pane.getChildren().addAll(radioButton1, radioButton2, radioButton3,
                answerLabel, answerTextField, saveAnswerButton);
        saveAnswerButton.setOnMouseClicked(mouseEvent1 -> {
            RadioButton selected = ((RadioButton) toggleGroup.getSelectedToggle());
            if (mouseEvent1.getButton() == MouseButton.PRIMARY) {
                if (selected != null && !answerTextField.getText().isEmpty()) {
                    if (selected.equals(radioButton1))
                        selectedQuestionNumber[0] = 1;
                    if (selected.equals(radioButton2))
                        selectedQuestionNumber[0] = 2;
                    if (selected.equals(radioButton3))
                        selectedQuestionNumber[0] = 3;
                    if (pane.getChildren().contains(error))
                        pane.getChildren().remove(error);

                    captchaScene(stage, selectedQuestionNumber[0], answerTextField.getText());
                } else {
                    if (!pane.getChildren().contains(error))
                        pane.getChildren().add(error);
                }
            }
        });
    }

    private void captchaScene(Stage stage, int number, String answer) {
        Pane pane = new Pane();
        TextArea area = new TextArea();
        //is the right method being called??
        area.setText(OtherController.generateCaptcha());
        area.setPrefColumnCount(15);
        area.setPrefHeight(120);
        area.setPrefWidth(300);
        area.relocate(40, 20);
        Label answerLabel = new Label("answer:");
        TextField textField = new TextField();
        Button changeButton = new Button("change");
        Button saveButton = new Button("save");
        answerLabel.relocate(40, 210);
        textField.relocate(40, 240);
        saveButton.relocate(40, 270);
        changeButton.relocate(300, 50);
        changeButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                area.setText(OtherController.generateCaptchaString());
            }
        });

        saveButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                FileController.addUserToFile(usernameTextField.getText(), passwordTextField.getText(),
                        emailTextField.getText(), nicknameTextField.getText(), sloganTextField.getText(),
                        number, answer);
                stage.close();
                if (OtherController.checkCaptcha(textField.getText())) {
                    if(startingMenu == null) {
                        startingMenu = new StartingMenu();
                    }
                    try {
                        startingMenu.start(StartingMenu.mainStage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        pane.getChildren().addAll(saveButton, answerLabel, textField, area, changeButton);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setScene(new Scene(pane));
    }


    private void updateUsernameError(Label label, String username) {
        //FileController.checkExistenceOfUserOrEmail(username,true)
        if (false)
            label.setText("The username exists");
        else
            label.setText("Invalid username");
    }


}
