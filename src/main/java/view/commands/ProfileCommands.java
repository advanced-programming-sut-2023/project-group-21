package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileCommands {
    CHANGE_SOMETHING("\\s*change\\s+(?<whichOption>\\S+)\\s+(?<newOption>(\\S+|\"[^\"]\"))\\s*"),
    CHANGE_PASSWORD("\\s*change\\s+password\\s+-o\\s+(?<oldPassword>(\\S+|\"[^\"]\"))\\s+-n\\s+" +
            "(?<newPassword>(\\S+|\"[^\"]\"))\\s+-c\\s+(?<confirm>(\\S+|\"[^\"]\"))\\s*"),
    LOGOUT("\\s*logout\\s*"),
    DISPLAY_PROFILE("\\s*display\\s*(?<part>.*)\\s*"),
    SHOW_MAP("\\s*show\\s+map\\s+-x\\s+(?<coordinateX>\\d+)\\s+-y\\s+(?<coordinateY>\\d+)\\s*"),
    HELP("\\s*help\\s*"),
    BACK("\\s*back\\s*");
    private final String regex;
    ProfileCommands(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, ProfileCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
