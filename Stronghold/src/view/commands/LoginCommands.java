
package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginCommands {
    LOGIN("\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(\\s+--stay-logged-in)?\\s*"),
    BACK("\\s*back\\s*"),
    HELP("\\s*help\\s*"),
    FORGET_PASSWORD("\\s*forget\\s+password\\s*"),
    FORGET_USERNAME("\\s*forget\\s+username");
    public String regex;
    LoginCommands(String regex) {
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, LoginCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
