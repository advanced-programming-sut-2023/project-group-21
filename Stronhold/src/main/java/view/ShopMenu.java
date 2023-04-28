package view;

import model.Government;
import view.commands.ShopMenuCommand;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    Government currentGovernment;
    public ShopMenu(Government government){
        this.currentGovernment = government;
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

    }
    private void buyCheck(Matcher matcher){
        String name = matcher.group("name");
        String intString = matcher.group("amount");
        int amount = Integer.parseInt(intString);

    }
    private void sellCheck(Matcher matcher){
        String name = matcher.group("name");
        String intString = matcher.group("amount");
        int amount = Integer.parseInt(intString);
    }
}
