
package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginCommands {
    LOGIN("\\s*login\\s+(?:(\\s*-u\\s+(?<username>(\\S+|\"[^\"]+\"))\\s*)|(\\s*-p\\s+(?<password>(\\S+|\"[^\"]\"))\\s*)){2}(\\s+(?<last>\\.*)--stay-logged-in)?\\s*"),
    BACK("\\s*back\\s*"),
    HELP("\\s*help\\s*"),
    FORGET_PASSWORD("\\s*forget\\s+password\\s+-u\\s+(?<username>(\\S+|\"[^\"]\"))\\s*"),
    FORGET_USERNAME("\\s*forget\\s+username\\s+-u\\s+(?<username>\\S+)\\s*"),
    NEW_PASSWORD("\\s*change\\s+password\\s+-p\\s+(?<password>(\\S+|\"[^\"]\"))\\s+-c\\s+(?<confirm>(\\S+|\"[^\"]\"))\\s*");

    private String regex;

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
