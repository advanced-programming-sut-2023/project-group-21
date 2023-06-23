package view;

import controller.TradingController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.generalenums.Resource;
import view.message.TradeMenuMessage;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class TradingMenu extends Application {
    private Pane pane;
    private Stage stage;
    private ArrayList<Government> governments = new ArrayList<>();
    private Government traderGovernment;
    private Scene tradeScene;
    private boolean addMenu;
    private Resource resource;
    private Label goldAmount;
    private Label name;
    private Label amount;
    private Label message;
    private Label selectedAmountLabel;
    private int selectedAmount;
    private Label selectedPriceLabel;
    private int selectedPrice;
    private Label errorLabel;
    private ArrayList<Label> receivedRequestLabels=new ArrayList<>();

    @Override
    public void start(Stage passedStage) throws Exception {
        URL url = StartMenu.class.getResource("/FXML/TradingMenu.fxml");
        pane = FXMLLoader.load(url);

        Rectangle rectangle = new Rectangle(700, 440);
        rectangle.relocate(50, 30);
        rectangle.setFill(Color.WHITE);
        rectangle.setOpacity(0.5);
        pane.getChildren().addAll(rectangle);
        stage = passedStage;


        //if selected label!=null make it red and 2 buttons to do the accept and reject and update that label when pressed and do the logic of
        //adding or substracting coin or resources

        if (addMenu) {
            addOfferMenu();
        }
        else
            showAllOffers();

        tradeScene = new Scene(pane);
        stage.setScene(tradeScene);
        stage.getIcons().add(new Image(StartMenu.class.getResourceAsStream("/images/logo.png")));
        stage.setTitle("shopping menu");
        stage.show();
    }

    private void showAllOffers() {
        User user1=new User("a","a","a","a","a");
        User user2=new User("s","s","s","s","s");
        Game.addTradeRequest(new TradeRequest(false,traderGovernment,new Government(user1,new Cell(10,10)),5,4,Resource.BOW));
        Game.addTradeRequest(new TradeRequest(true,traderGovernment,new Government(user2,new Cell(10,10)),5,4,Resource.BOW));
        Game.addTradeRequest(new TradeRequest(false,new Government(user2,new Cell(10,10)),traderGovernment,5,4,Resource.BOW));
        Game.addTradeRequest(new TradeRequest(true,new Government(user1,new Cell(10,10)),traderGovernment,5,4,Resource.BOW));
        addReceivedOffers(getReceivedOffers(traderGovernment));
        addSentOffers(getSentOffers(traderGovernment));
    }

    private ArrayList<TradeRequest> getReceivedOffers(Government traderGovernment) {
        ArrayList<TradeRequest> result=new ArrayList<>();
        for (TradeRequest request : Game.getTradeRequests()) {
            if(request.getReceiver().equals(traderGovernment))
                result.add(request);
        }
        return result;
    }

    private ArrayList<TradeRequest> getSentOffers(Government traderGovernment) {
        ArrayList<TradeRequest> result=new ArrayList<>();
        for (TradeRequest request : Game.getTradeRequests()) {
            if(request.getSender().equals(traderGovernment))
                result.add(request);
        }
        return result;
    }

    private void addReceivedOffers(ArrayList<TradeRequest> requests){
        Label title=new Label("received");
        title.setId("title2");
        title.relocate(190,40);
        Line separator=new Line(400,60,400,470);


        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setPrefSize(320,340);
        scrollPane.relocate(70,120);
        VBox vBox=new VBox(10);
        for (TradeRequest request : requests) {
            String type;
            if(request.isBuyRequest())
                type="sell";
            else
                type="buy";
            Label requestLabel=new Label(request.getSender().getLord().getUserName()+" | "+request.getAmount()+" | "+request.getPrice()+ " | " +type+" | "+request.getStatus());
            requestLabel.setId("showOffers");
            receivedRequestLabels.add(requestLabel);
            vBox.getChildren().add(requestLabel);
        }
        scrollPane.setContent(vBox);

        pane.getChildren().addAll(scrollPane,title,separator);

    }

    private void addSentOffers(ArrayList<TradeRequest> requests){
        Label title=new Label("sent");
        title.setId("title2");
        title.relocate(540,40);


        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setPrefSize(320,340);
        scrollPane.relocate(410,120);
        VBox vBox=new VBox(10);
        for (TradeRequest request : requests) {
            String type;
            if(request.isBuyRequest())
                type="sell";
            else
                type="buy";
            Label requestLabel=new Label(request.getReceiver().getLord().getUserName()+" | "+request.getAmount()+" | "+request.getPrice()+ " | " +type+" | "+request.getStatus());
            requestLabel.setId("showOffers");
            receivedRequestLabels.add(requestLabel);
            vBox.getChildren().add(requestLabel);
        }
        scrollPane.setContent(vBox);

        pane.getChildren().addAll(scrollPane,title);

    }

    private void addCountControls() {
        selectedAmountLabel = new Label();
        selectedAmountLabel.relocate(690, 320);
        selectedPriceLabel = new Label();
        selectedPriceLabel.relocate(690, 350);
        Label increaseAmount = new Label(" + ");
        Label decreaseAmount = new Label(" - ");
        increaseAmount.relocate(620, 310);
        decreaseAmount.relocate(590, 308);
        increaseAmount.setId("increase");
        decreaseAmount.setId("increase");
        selectedAmount = 1;
        selectedPrice = 0;

        Label increasePrice = new Label(" + ");
        Label decreasePrice = new Label(" - ");
        increasePrice.relocate(620, 340);
        decreasePrice.relocate(590, 338);
        increasePrice.setId("increase");
        decreasePrice.setId("increase");

        increaseAmount.setOnMouseClicked(mouseEvent -> {
            selectedAmount++;
        });
        decreaseAmount.setOnMouseClicked(mouseEvent -> {
            if (selectedAmount > 1)
                selectedAmount--;
        });

        increasePrice.setOnMouseClicked(mouseEvent -> {
            selectedPrice++;
        });
        decreasePrice.setOnMouseClicked(mouseEvent -> {
            if (selectedPrice > 0)
                selectedPrice--;
        });

        pane.getChildren().addAll(selectedAmountLabel, selectedPriceLabel, increaseAmount, increasePrice, decreaseAmount, decreasePrice);
    }

    private void updateLabel() {
        name.setText("resource name: " + resource.getName());
        if (traderGovernment.getResourceAmount(resource) < 0)
            amount.setText("amount: " + 0);
        else
            amount.setText("amount: " + String.valueOf(traderGovernment.getResourceAmount(resource)));
        goldAmount.setText("gold: " + traderGovernment.getGold());
        selectedAmountLabel.setText(String.valueOf(selectedAmount));
        selectedPriceLabel.setText(String.valueOf(selectedPrice));
    }

    private void addLabels() {
        Label title = new Label("trade");
        title.setId("title");
        title.relocate(380, 40);
        Label priceLabel = new Label("price:");
        Label amountLabel = new Label("amount:");
        errorLabel = new Label();
        errorLabel.setId("error");
        errorLabel.relocate(60, 350);
        priceLabel.relocate(510, 350);
        amountLabel.relocate(510, 320);
        goldAmount = new Label();
        name = new Label();
        amount = new Label();
        goldAmount.relocate(660, 100);
        name.relocate(540, 60);
        amount.relocate(540, 100);
        message = new Label();
        message.relocate(40, 200);
        message.setId("error");
        pane.getChildren().addAll(amount, name, title, goldAmount, amountLabel, priceLabel);
    }

    private void addOfferMenu() {
        resource = Resource.GOLD;
        addLabels();
        addCountControls();
        Timeline labelsTimeline = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            updateLabel();
        }));
        labelsTimeline.setCycleCount(-1);
        labelsTimeline.play();
        addPictures();
        addButtons();
    }

    private void addPictures() {
        resourceInShop(Resource.WHEAT, 60, 60);
        resourceInShop(Resource.APPLE, 100, 60);
        resourceInShop(Resource.BREAD, 140, 60);
        resourceInShop(Resource.MEAT, 180, 60);
        resourceInShop(Resource.FLOUR, 220, 60);
        resourceInShop(Resource.IRON, 60, 100);
        resourceInShop(Resource.WOOD, 100, 100);
        resourceInShop(Resource.STONE, 140, 100);
        resourceInShop(Resource.PITCH, 180, 100);
        resourceInShop(Resource.HORSE, 220, 100);
        resourceInShop(Resource.LEATHER_ARMOR, 60, 140);
        resourceInShop(Resource.METAL_ARMOR, 100, 140);
        resourceInShop(Resource.BOW, 140, 140);
        resourceInShop(Resource.CROSSBOW, 180, 140);
        resourceInShop(Resource.MACE, 220, 140);
        resourceInShop(Resource.SPEAR, 60, 180);
        resourceInShop(Resource.SWORD, 100, 180);
        resourceInShop(Resource.ALE, 140, 180);
        resourceInShop(Resource.HOPS, 180, 180);
        resourceInShop(Resource.PIKE, 220, 180);
    }

    private void resourceInShop(Resource circleResource, int x, int y) {
        Circle circle = new Circle(18);
        if (traderGovernment.getResourceAmount(circleResource) < 1)
            circle.setOpacity(0.4);
        Image image = new Image(StartMenu.class.getResourceAsStream("/images/resources/" + circleResource.getName() + ".png"));
        circle.setStroke(Color.BLACK);
        circle.setFill(new ImagePattern(image));
        circle.relocate(x, y);
        circle.setOnMouseClicked(mouseEvent -> {
            this.resource = circleResource;
            this.selectedAmount = 1;
        });
        pane.getChildren().add(circle);
    }

    private void addButtons() {
        ChoiceBox choiceBox = new ChoiceBox();

        for (Government government : governments) {
            choiceBox.getItems().add(government.getLord().getUserName());
        }

        Button requestButton = new Button("request");
        Button donateButton = new Button("donate");
        Button back = new Button("back");
        choiceBox.relocate(540, 260);
        donateButton.relocate(540, 400);
        requestButton.relocate(630, 400);
        back.relocate(100, 400);

        back.setOnMouseClicked(mouseEvent -> {
            stage.close();
            ShoppingMenu shoppingMenu = new ShoppingMenu();
            shoppingMenu.setGovernment(traderGovernment);
            try {
                shoppingMenu.start(StartingMenu.mainStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        donateButton.setOnMouseClicked(mouseEvent -> {
            TradingController tradingController = new TradingController();
            if (!resource.equals(Resource.GOLD)) {
                if (choiceBox.getSelectionModel().getSelectedItem() != null) {
                    Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
                    Government receiver = getGovernmentByName((String) selectedItem);
                    TradeRequest tradeRequest = new TradeRequest(true, traderGovernment, receiver, selectedAmount, selectedPrice, resource);
                    TradeMenuMessage message = tradingController.checkDonate(tradeRequest);
                    updateError(message.toString());
                    if (message.equals(TradeMenuMessage.SUCCESS)) {
                        stage.close();
                    }
                }
                updateError("select a government!");
            } else
                updateError("select a resource other than gold!");
        });

        requestButton.setOnMouseClicked(mouseEvent -> {
            TradingController tradingController = new TradingController();
            if (!resource.equals(Resource.GOLD)) {
                if (choiceBox.getSelectionModel().getSelectedItem() != null) {
                    Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
                    Government receiver = getGovernmentByName((String) selectedItem);
                    TradeRequest tradeRequest = new TradeRequest(false, traderGovernment, receiver, selectedAmount, selectedPrice, resource);
                    TradeMenuMessage message = tradingController.checkRequest(tradeRequest);
                    updateError(message.toString());
                    if (message.equals(TradeMenuMessage.SUCCESS)) {
                        stage.close();
                    }
                } else
                    updateError("select a government!");
            } else
                updateError("select a resource other than gold!");
        });

        pane.getChildren().addAll(requestButton, donateButton, back, choiceBox);
    }

    private Government getGovernmentByName(String username) {
        for (Government government : governments) {
            if (government.getLord().getUserName().equals(username))
                return government;
        }
        return null;
    }

    private void updateError(String message) {
        errorLabel.setText(message);
        if (!pane.getChildren().contains(errorLabel))
            pane.getChildren().add(errorLabel);
    }


    public void setGovernment(Government government) {
        this.traderGovernment = government;
    }

    public void addGovernments(Government government) {
        governments.add(government);
    }

    public void isAddMenu(boolean addMenu) {
        this.addMenu = addMenu;
    }
}
