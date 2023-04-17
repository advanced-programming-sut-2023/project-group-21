package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StartMenuCommand {
    GO_TO_SIGNUP_MENU("\\s*sign-up\\s*"),
    GO_TO_SIGN_IN("\\s*sign-in\\s+");
    private String regex;
    StartMenuCommand(String regex){
        this.regex=regex;
    }
    public static Matcher getMatcher(String input, StartMenuCommand mainRegex){
        Matcher matcher= Pattern.compile(mainRegex.regex).matcher(input);
        return matcher.matches()?matcher:null;
    }
}
