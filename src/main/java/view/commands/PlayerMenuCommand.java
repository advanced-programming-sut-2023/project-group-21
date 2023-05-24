package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PlayerMenuCommand {
    GO_TO_MENU("\\s*go\\s+to\\s+(?<menuName>(\\S+|\"[^\"]\"))\\s+menu\\s*"),
    START_GAME_WITH("\\s*start\\s+game\\s+with\\s+[(\\S+)]*\\s*");


    private String regex;
    PlayerMenuCommand(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, PlayerMenuCommand mainRegex) {
        java.util.regex.Matcher matcher = Pattern.compile(mainRegex.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
