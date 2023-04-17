package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommand {
    SHOW_FACTORS("\\s*show\\s+popularity\\s+factor\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    SET_FOOD_RATE("\\s*food\\s+rate\\s+-r\\s+(?<rate>\\d+)\\s*");
    private String regex;
    GameMenuCommand(String regex){
        regex = regex;
    }
    public static Matcher getMatcher(String input, GameMenuCommand mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
