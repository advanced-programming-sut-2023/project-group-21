package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommand {
    BUY("\\s*buy\\s+-a\\s+(?<amount>\\d+)\\s+-n\\s+(?<name>\\S+)\\s*"),
    SELL("\\s*sell\\s+-a(?<amount>\\d+)\\s+-n\\s+(?<name>\\S+)\\s*"),
    SHOW_ITEM("\\s*show\\s+item\\s*");


    private String regex;
    ShopMenuCommand(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, ShopMenuCommand mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
