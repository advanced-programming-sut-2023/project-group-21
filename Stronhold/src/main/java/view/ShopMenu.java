package view;

import controller.ShopController;
import model.Government;
import view.commands.ShopMenuCommand;
import view.message.ShopMessage;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    Government currentGovernment;
    ShopController shopController;
    public ShopMenu(Government government){
        this.currentGovernment = government;
        shopController = new ShopController(government);
    }
    public void run(Scanner scanner){
        String line;
        Matcher matcher;
        while (true){
            line = scanner.nextLine();
            if(line.equals("back"))
                break;
            else if((matcher = ShopMenuCommand.getMatcher(line,ShopMenuCommand.BUY))!=null)
                buyCheck(matcher);
            else if((matcher = ShopMenuCommand.getMatcher(line,ShopMenuCommand.SELL))!=null)
                sellCheck(matcher);
            else if((matcher = ShopMenuCommand.getMatcher(line,ShopMenuCommand.SHOW_ITEM))!=null)
                showPrice();
        }
    }
    private void showPrice(){
        System.out.print(shopController.showDetails());
    }
    private void buyCheck(Matcher matcher){
        String name = matcher.group("name");
        String intString = matcher.group("amount");
        int amount = Integer.parseInt(intString);
        ShopMessage message = shopController.buy(name,amount);
    }
    private void sellCheck(Matcher matcher){
        String name = matcher.group("name");
        String intString = matcher.group("amount");
        int amount = Integer.parseInt(intString);
        ShopMessage message = shopController.sell(name,amount);
    }
}
