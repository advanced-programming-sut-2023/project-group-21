package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpCommands {
    s("");
    private String regex;
    private SignUpCommands(String regex){
        this.regex=regex;
    }

    public static Matcher getMatcher(String input, SignUpCommands mainRegex){
        Matcher matcher= Pattern.compile(mainRegex.regex).matcher(input);
        return matcher.matches()?matcher:null;
    }
}
