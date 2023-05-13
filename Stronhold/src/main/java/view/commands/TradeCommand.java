package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeCommand {
    SEND_REQUEST("\\s*send\\s+request\\s+-t\\s+(?<resource>\\S+)\\s+-a\\s+" +
            "(?<amount>\\d+)\\s+-p\\s+(?<price>\\d+)\\s+-m\\s+(?<message>.+)"),
    SHOW_LIST("\\s*trade\\s+list\\s*"),
    ACCEPT_TRADE("accept\\s+trade\\s+-i\\s+(?<id>\\d+)\\s+-m\\s+(?<message>.+)"),
    BACK("\\s*back\\s*"),
    TRADE_HISTORY("show\\s+history\\s+");
    private final String regex;

    TradeCommand(String regex) {
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, TradeCommand mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
