package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignUpCommands {
    REGISTER_WITH_PASSWORD(
            "\\s*create\\s+user\\s+-u\\s+(?<username>(\"[^\"]+\"|\\S+))\\s+-p\\s+(?<password>(\\S+|\"[^\"]*\"))\\s+(?<confirm>(\\S+|\"[^\"]*\"))\\s+-e\\s+(?<email>(\\S+|\"[^\"]*\"))(\\s+-s\\s+(?<slogan>(\\S+|\"[^\"]*\")))?(\\s+-n\\s+(?<nickname>(\\S+|\"[^\"]*\")))?\\s*$"),
    REGISTER_WITH_RANDOM_PASSWORD(
            "\\s*create\\s+user\\s+-u\\s+(?<username>(\"[^\"]*\"|\\S+))\\s+-p\\s+random(\\s+-e\\s+(?<email>(\\S+|\"[^\"]*\")))(\\s+-s\\s+(?<slogan>(\\S+|\"[^\"]*\")))?(\\s+-n\\s+(?<nickname>(\\S+|\"[^\"]*\")))?\\s*$"),
    HELP("\\s*help\\s*"),
    BACK("\\s*back\\s*"),
    PICK_QUESTION(
            "\\s*pick\\s+question\\s+-q\\s+(?<number>\\d+)\\s+-a\\s+(?<answer>(\\S+|\"[^\"]*\"))\\s+-c\\s+(?<confirm>(\\S+|\"[^\"]*\"))\\s*");

    private String regex;

    SignUpCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, SignUpCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
