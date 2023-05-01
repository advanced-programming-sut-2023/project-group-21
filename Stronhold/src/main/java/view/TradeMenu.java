package view;

import controller.TradeController;
import model.Game;
import model.Government;
import model.Trade;
import view.commands.TradeCommand;
import view.message.TradeMenuMessage;

//import javax.naming.InsufficientResourcesException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public void run(Scanner scanner,Government government) {
        String line = " ";
        Matcher matcher;
        //show the list of players
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
        }
    }

    private void checkTrade(Matcher matcher,Government government){
        String resource=matcher.group("resource");
        int amount= Integer.parseInt(matcher.group("amount"));
        int price=Integer.parseInt(matcher.group("price"));
        String message=matcher.group("message");
        TradeMenuMessage tradeMenuMessage=new TradeController().trade(resource,amount,price,message,government);
        System.out.println(tradeMenuMessage.toString());
//        if the to string method is overwritten correctly there's no need to switch case
//        switch (tradeMenuMessage){
//            case INVALID_RESOURCE_TYPE:
//                System.out.println(tradeMenuMessage.toString());
//                break;
//            case INVALID_AMOUNT:
//                System.out.println("Amount is invalid");
//                break;
//            case INVALID_PRICE:
//                System.out.println("Price is invalid");
//                break;
//            case INADEQUATE_AMOUNT:
//                System.out.println("Not enough amount of the resource");
//                break;
//            case SUCCESS:
//                System.out.println("Trade successfully added");
//                break;
//        }
    }

    private void sendRequest(Matcher matcher){

    }

    private void checkAccept(Matcher matcher,Government government){
        int id=Integer.parseInt(matcher.group("id"));
        String message= matcher.group("message");
        TradeMenuMessage tradeMenuMessage=TradeController.accept(id,message,government);

    }

    private void showTradeList(){
        String show="";
        for (Trade trade : Game.getTrades()) {
            show = show + trade.getId() + " " + trade.getResource().getName() + trade + " " + trade.getAmount() + " " + trade.getCost() + " " + trade.getComment() + "\n";
        }
        System.out.println(show);
    }
    //No need??
    //What happens if we do not check the price
    private void tradeAcceptAsGift(Matcher matcher){

    }

    private void showTradeHistory(Government government){
        String show="";
        for (Trade trade : government.getTrades()) {
            show = show + trade.getId() + " " + trade.getResource().getName() + trade + " " + trade.getAmount() + " " + trade.getCost() + " " + trade.getComment() + "\n";
        }

        System.out.println(show);
    }

}
