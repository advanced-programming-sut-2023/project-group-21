package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapCommand {
    BACK("back"),
    SHOW_MAP("\\s*show\\s+map\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    MOVE_MAP("\\s+move\\s+(?<left>.*)"),
    CLEAR("\\s*clear\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)"),
    SET_TEXTURE("\\s*set\\s+texture\\s+-x1\\s+(?<x1>\\d+)\\s+-y1\\s+(?<y1>\\d+)\\s+-x2\\s+(?<x2>\\d+)\\s+y2\\s+(?<y2>\\d+)\\s+-t\\s+(?<type>\\S+)\\s*"),
    DROP_TREE("\\s*drop\\s+tree\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s+-t\\s+(?<type>\\S+)\\s*"),
    DROP_ROCK("\\s*drop\\s+rock\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s+-t\\s+(?<type>\\S+)\\s*"),
    SET_TEXTURE_ONE_SQUARE("\\s*set\\s+texture\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s+-t\\s+(?<type>\\S+)\\s*"),
    SAVE_MAP("\\s*save\\s+map\\s*"),
    MAKE_NEW_MAP("\\s*make\\s+new\\s+map\\s*-s\\s+(?<size>\\d+)\\s*");


    private final String regex;
    MapCommand(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, MapCommand mainRegex) {
        java.util.regex.Matcher matcher = Pattern.compile(mainRegex.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }

}
