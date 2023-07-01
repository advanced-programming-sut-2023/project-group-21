package view;

import controller.TradeController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import model.generalenums.MessageEnum;
import view.commands.TradeCommand;
import view.message.TradeMenuMessage;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public void run(Scanner scanner,Government government) {
        String line = " ";
        Matcher matcher;
        while (true) {
            line = scanner.nextLine();
            if ((matcher = TradeCommand.getMatcher(line, TradeCommand.BACK)) != null)
                break;
            else if ((matcher = TradeCommand.getMatcher(line, TradeCommand.SEND_REQUEST)) != null)
                checkTrade(matcher,government);
            else if((matcher=TradeCommand.getMatcher(line,TradeCommand.SHOW_LIST)) != null)
                showTradeList();
            else if((matcher=TradeCommand.getMatcher(line,TradeCommand.TRADE_HISTORY)) != null)
                showTradeHistory(government);
            else if((matcher=TradeCommand.getMatcher(line,TradeCommand.ACCEPT_TRADE)) != null)
                checkAccept(matcher,government);
            else if(line.equals("help"))
                help();
            else
                System.out.println("Invalid input");
        }
    }

    private void help() {
        System.out.println("You are in the trade menu.You can sell\nbuy and check the history of trades");
    }

    private void checkTrade(Matcher matcher,Government government){
        String resource=matcher.group("resource");
        int amount= Integer.parseInt(matcher.group("amount"));
        int price=Integer.parseInt(matcher.group("price"));
        String message=matcher.group("message");
        TradeMenuMessage tradeMenuMessage=new TradeController().trade(resource,amount,price,message,government);
        System.out.println(tradeMenuMessage.toString());
    }

    private void checkAccept(Matcher matcher,Government government){
        int id=Integer.parseInt(matcher.group("id"));
        String message= matcher.group("message");
        TradeMenuMessage tradeMenuMessage=TradeController.accept(id,message,government);
    }

    private void showTradeList(){
        String show="";
        for (Trade trade : Game.getTrades()) {
            show = show + trade.getId() + " " + trade.getResource().getName() + " " + trade.getAmount() +
                    " " + trade.getCost() + " " + trade.getComment() + "\n";
        }
        System.out.print(show);
    }

    private void showTradeHistory(Government government){
        String show="";
        for (Trade trade : government.getTrades()) {
            show = show + trade.getId() + " " + trade.getResource().getName() + trade + " " +
                    trade.getAmount() + " " + trade.getCost() + " " + trade.getComment() + "\n";
        }
        System.out.print(show);
    }

    public static class Test extends Application {
        Message message = new Message("ali","this is only for test", MessageEnum.ROOM);

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            MessageGui messageGui = new MessageGui(message,true);
            Pane pane = new Pane();
            pane.getChildren().add(messageGui.getPane());
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
