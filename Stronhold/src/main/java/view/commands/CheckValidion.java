package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CheckValidion {
    CHECK_EMAIL("[A-Za-z0-9_\\.]+@[A-Za-z_\\.]+\\.[A-Za-z]+"),
    CHECK_USERNAME("[A-Za-z0-9_]+"),
    CHECK_PASSWORD(
            "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[\\*\\.!@%\\^&\\(\\)\\{\\}\\[\\]:?;\\<>,~_+\\-=\\|]).{6,32}$");
    private String regex;
    CheckValidion(String regex){
        this.regex = regex;
    }
    public static boolean check(String input, CheckValidion mainRegex){
        Matcher matcher= Pattern.compile(mainRegex.regex).matcher(input);
        if(matcher.matches())
            return true;
        return false;
    }
}
