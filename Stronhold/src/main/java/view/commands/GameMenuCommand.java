package view.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommand {
    SHOW_FACTORS("\\s*show\\s+popularity\\s+factor\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    SET_FOOD_RATE("\\s*food\\s+rate\\s+-r\\s+(?<rate>\\d+)\\s*"),
    SET_TAX("\\s*tax\\s+rate\\s+-r\\s+(?<rate>\\d+)\\s*"),
    SHOW_TAX_RATE("\\s*show\\s*tax\\s+rate\\s*"),
    DROP_BUILDING("\\s*drop\\s*building\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s+-t\\s+(?<type>(\\S+|\"[^\"]*\"))\\s*"),
    GO_TO_SHOP("\\s*go\\s+to\\s+shop\\s*"),
    SELECT_BUILDING("\\s*select\\s+building\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    SHOW_FOOD_RATE("\\s*show\\s+food\\s+rate\\s*"),
    CREATE_UNIT("\\s*create\\s+unit\\s+-t\\s+(?<type>\\S+)\\s+-c\\s+(?<count>\\d+)\\s+-x (?<x>\\d+) -y (?<y>\\d+)\\s*"),
    REPAIR("\\s*repair\\s*"),
    SELECT_UNIT("\\s*select\\s+unit\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    MOVE_UNIT("\\s*move\\s+unit\\s+to\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    CHANGE_STATE("\\s*set\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    ATTACK("\\s*attack\\s+-e\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    ATTACK2("\\s*attack\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    POUR_OIL("\\s*pour\\s+oil\\s+-d\\s+(?<direction>\\d+)s*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s+(?<direction>\\S+)"),
    DISBAND_UNIT("\\s*disband\\s+unit\\s*"),
    NEXT_TURN("\\s*next\\s+turn\\s*"),
    SELECT_MACHINE("move\\s+machine\\s+-x"),//repair!
    GO_TO_TRADE("\\s*go\\s+to\\s+trade\\s*"),
    PATROL("\\s*patrol\\s+-x1\\s+(?<x1>\\d+)\\s+-y1\\s+(?<y1>\\d+)\\s+-x2\\s+(?<x2>\\d+)\\s+(?<y2>\\d+)\\s*"),
    MOVE_EQUIPMENT("move equipment -x1 (?<x1>\\d+) -y1 (?<y1>\\d+) -x2 (?<x2>\\d+) -y2 (?<y2>\\d+)"),
    MAKE__("make equipment (?<name>(\\S+|\"[^\"]*\"))"),
    SWITCH("\\s*switch\\s*"),
    SHOW_RESOURCE("\\s*show\\s+resource\\s+(?<resource>\\S+)\\s*"),
    SET_FEAR_RATE("set fear rate -r (?<rate>-?\\d+)\\s*"),
    CHANGE_GATE("\\s*(?<state>\\S+)\\s+gate\\s*"),
    CALCULATE_UNEMPLOYMENT("\\s*show\\s+unemployment\\s*");
    private final String regex;
    GameMenuCommand(String regex){
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, GameMenuCommand mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
