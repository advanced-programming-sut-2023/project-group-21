package view;

import controller.FileController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;
import java.net.URL;

public class StartingMenu extends Application {
    private static ObjectInputStream dIn;
    private static ObjectOutputStream dOut;
    private static OutputStream outputStream;
    private static InputStream inputStream;
    private static Socket socket;

    static {
        try {
            socket = new Socket("localhost", 8080);
            outputStream = socket.getOutputStream();
            dOut = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            dIn = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stage mainStage;
    public static void main(String[] args) throws IOException {
        FileController.start();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/StartingMenu.fxml");
        Pane mainPane = FXMLLoader.load(url);
        mainStage=stage;
        addButtons(mainPane);
        addOtherGraphics(mainPane);

        Scene scene=new Scene(mainPane);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("Stronghold");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    dOut.writeObject("logout");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
                signingMenu.setStartingMenu(this);
                try {
                    signingMenu.start(mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        loginButton.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()==MouseButton.PRIMARY){
                LoggingMenu loggingMenu=new LoggingMenu();
                loggingMenu.setStartingMenu(this);
                try {
                    loggingMenu.start(mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        quitButton.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()== MouseButton.PRIMARY) {
//                Profile profile=new Profile();
//                profile.setUser(new User("sobhan","sO2@1aAD*","solo","sobhan@gmail.com","i am here!"));
//                try {
//                    profile.start(mainStage);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
                FileController.finish();
                Platform.exit();
            }
        });

        pane.getChildren().addAll(signUpButton,loginButton,quitButton);
    }

    private void addOtherGraphics(Pane mainPane) {
        Line upperLine=new Line(10,10,1070,10);
        Line rightLine=new Line(1070,10,1070,170);
        Line leftLine=new Line(10,10,10,680);
        Line upperLine2=new Line(15,15,1065,15);
        Line rightLine2=new Line(1065,15,1065,170);
        Line leftLine2=new Line(15,15,15,680);
        Line downLine1=new Line(10,680,15,680);
        Line downLine2=new Line(1065,170,1070,170);
        Label label=new Label("GROUP 21 - 2023 SPRING");
        label.setFont(Font.font(12));
        label.setRotate(90);
        label.relocate(-40,600);

        mainPane.getChildren().addAll(upperLine,leftLine,rightLine,label,
                upperLine2,rightLine2,leftLine2,downLine1,downLine2);
    }

    public static ObjectInputStream getDIn() {
        return dIn;
    }

    public static ObjectOutputStream getDOut() {
        return dOut;
    }

    public static OutputStream getOutputStream() {
        return outputStream;
    }

    public static InputStream getInputStream() {
        return inputStream;
    }
}
