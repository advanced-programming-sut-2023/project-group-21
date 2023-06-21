package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Government;

import java.net.URL;
import java.util.ArrayList;

public class TradingMenu extends Application {
    private Pane pane;
    private ArrayList<Government> governments=new ArrayList<>();
    private Government traderGovernment;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/TradingMenu.fxml");
        pane = FXMLLoader.load(url);
        Stage tradeStage=new Stage();
        Scene scene=new Scene(pane);
        tradeStage.setScene(scene);
        tradeStage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        tradeStage.setTitle("shopping menu");
        tradeStage.show();
    }

    public void setGovernment(Government government) {
        this.traderGovernment=government;
    }

    public void addGovernments(Government government) {
        governments.add(government);
    }
}
