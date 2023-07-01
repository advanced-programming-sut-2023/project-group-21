package view;

import controller.GameController;
import controller.ShopController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import ServerConnection.Cell;
import model.Game;
import model.Government;
import ServerConnection.User;
import model.generalenums.Resource;

import java.net.URL;


public class ShoppingMenu extends Application {
    private Government government;
    private Resource resource;
    private Pane pane;
    private Label name;
    private Label amount;
    private Label sellPrice;
    private Label buyPrice;
    private Label goldAmount;
    private Label selected;
    private Label message;
    private int selectedAmount=1;
    private Stage shopStage;
    private GameController gameController;

    @Override
    public void start(Stage stage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/ShoppingMenu.fxml");
        pane = FXMLLoader.load(url);
        Scene scene=new Scene(pane);
        shopStage=new Stage();

        Timeline labelsTimeline=new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            updateLabel();
        }));
        labelsTimeline.setCycleCount(-1);
        labelsTimeline.play();
        addPictures();
        addLabels();
        addButtons();


        shopStage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        shopStage.setTitle("shopping menu");
        shopStage.setScene(scene);
        shopStage.show();
    }

    private void addButtons() {
        Button sell =new Button("sell");
        Button buy=new Button("buy");
        Label increase=new Label(" + ");
        Label decrease=new Label(" - ");
        Button addOffer=new Button("addOffer");
        Button allOffers=new Button("allOffers");
        allOffers.relocate(60,260);
        addOffer.relocate(60,300);
        increase.relocate(580,220);
        decrease.relocate(540,218);
        increase.setId("increase");
        decrease.setId("increase");


        addOffer.setOnMouseClicked(mouseEvent -> {
            TradingMenu tradingMenu=new TradingMenu();
            Government government=new Government(new User("s","s","s","s","s",0),new Cell(20,20));
            tradingMenu.setGovernment(government);
            tradingMenu.addGovernments(new Government(new User("a","a","a","a","a",0),new Cell(10,10)));
            tradingMenu.addGovernments(new Government(new User("d","d","d","d","d",0),new Cell(18,18)));
            tradingMenu.isAddMenu(true);
            try {
                tradingMenu.start(shopStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        allOffers.setOnMouseClicked(mouseEvent -> {
            TradingMenu tradingMenu=new TradingMenu();
            Government government=new Government(new User("s","s","s","s","s",0),new Cell(20,20));
            tradingMenu.setGovernment(government);
            tradingMenu.setGovernment(this.government);
            for (Government gameControllerGovernment : gameController.getGovernments()) {
                tradingMenu.addGovernments(gameControllerGovernment);
            }
            try {
                tradingMenu.start(shopStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        increase.setOnMouseClicked(mouseEvent -> {
            selectedAmount++;
        });
        decrease.setOnMouseClicked(mouseEvent -> {
            if(selectedAmount>1)
                selectedAmount--;
        });
        sell.relocate(540,180);
        buy.relocate(660,180);

        ShopController shopController=new ShopController(government);
        buy.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()== MouseButton.PRIMARY){
                addMessage(shopController.buyResource(resource,selectedAmount).toString());
            }
        });
        sell.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()==MouseButton.PRIMARY){
                addMessage(shopController.sellResource(resource,selectedAmount).toString());
            }
        });

        pane.getChildren().addAll(sell,buy,increase,decrease,addOffer,allOffers);
    }

    private void addMessage(String string) {
        message.setText(string);
        if(!pane.getChildren().contains(message))
            pane.getChildren().add(message);
    }

    private void addLabels() {
        Label title=new Label("Shop");
        title.setId("title");
        title.relocate(380,10);
        goldAmount=new Label();
        name=new Label();
        amount=new Label();
        sellPrice=new Label();
        buyPrice=new Label();
        selected=new Label();
        selected.relocate(660,230);
        goldAmount.relocate(660,100);
        name.relocate(540,60);
        amount.relocate(540,100);
        buyPrice.relocate(540,140);
        sellPrice.relocate(660,140);
        message=new Label();
        message.relocate(40,200);
        message.setId("error");
        pane.getChildren().addAll(amount,name,buyPrice,sellPrice,title,goldAmount,selected);
    }

    private void addPictures() {
        resourceInShop(Resource.WHEAT,20,60);
        resourceInShop(Resource.APPLE,60,60);
        resourceInShop(Resource.BREAD,100,60);
        resourceInShop(Resource.MEAT,140,60);
        resourceInShop(Resource.FLOUR,180,60);
        resourceInShop(Resource.IRON,20,100);
        resourceInShop(Resource.WOOD,60,100);
        resourceInShop(Resource.STONE,100,100);
        resourceInShop(Resource.PITCH,140,100);
        resourceInShop(Resource.HORSE,180,100);
        resourceInShop(Resource.LEATHER_ARMOR,20,140);
        resourceInShop(Resource.METAL_ARMOR,60,140);
        resourceInShop(Resource.BOW,100,140);
        resourceInShop(Resource.CROSSBOW,140,140);
        resourceInShop(Resource.MACE,180,140);
        resourceInShop(Resource.SPEAR, 20, 180);
        resourceInShop(Resource.SWORD, 60, 180);
        resourceInShop(Resource.ALE, 100, 180);
        resourceInShop(Resource.HOPS, 140, 180);
        resourceInShop(Resource.PIKE, 180, 180);
    }

    private void resourceInShop(Resource resource, int x, int y){
        Circle circle = new Circle(18);
        Image image = new Image(StartMenu.class.getResourceAsStream("/images/resources/"+resource.getName()+".png"));
        circle.setStroke(Color.BLACK);
        circle.setFill(new ImagePattern(image));
        circle.relocate(x, y);
        circle.setOnMouseClicked(mouseEvent -> {
            this.resource=resource;
            this.selectedAmount=1;
        });
        pane.getChildren().add(circle);
    }

    public void setGovernment(Government government) {
        this.resource=Resource.APPLE;
        this.government=government;
    }
    //-1
    public void updateLabel(){
            name.setText("resource name: "+resource.getName());
            if(government.getResourceAmount(resource)<0)
                amount.setText("amount: "+0);
            else
                amount.setText("amount: "+String.valueOf(government.getResourceAmount(resource)));
            sellPrice.setText("sell price: "+resource.getCostSell());
            buyPrice.setText("buy price: "+resource.getCostBuy());
            goldAmount.setText("gold: "+government.getGold());
            selected.setText(String.valueOf(selectedAmount));
    }

    public void setGameController(GameController gameController) {
        this.gameController=gameController;
    }
}
