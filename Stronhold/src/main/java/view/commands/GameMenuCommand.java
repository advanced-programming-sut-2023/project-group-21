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
    DROP_BUILDING("\\s*drop\\s*building\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s+-t\\s+(?<type>\\S+)\\s+"),
    GO_TO_SHOP("\\s*go\\s+to\\s+shop\\s*"),
    SELECT_BUILDING("\\s*select\\s+building\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    SHOW_FOOD_RATE("\\s*show\\s+food\\s+rate\\s*"),
    CREATE_UNIT("\\s*create\\s+unit\\s+-t\\s+(?<type>\\S+)\\s+-c\\s+(?<count>\\d+)\\s*"),
    REPAIR("\\s*repair\\s*"),
    SELECT_UNIT("\\s*select\\s+unit\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    MOVE_UNIT("\\s*move\\s+unit\\s+to\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    CHANGE_STATE("\\s*set\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    ATTACK("\\s*attack\\s+-e\\s+(?<enemy>\\S+)\\s*"),
    ATTACK2("\\s*attack\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    POUR_OIL("\\s*pour\\s+oil\\s+-d\\s+(?<direction>\\d+)s*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel\\s+-x\\s+(?<x>\\d+)\\s+-y\\s+(?<y>\\d+)\\s*"),
    DISBAND_UNIT("\\s*disband\\s+unit\\s*"),
    NEXT_TURN("\\s*next\\s+turn\\s*"),
    SELECT_MACHINE("move\\s+machine\\s+-x"),//repair!
    GO_TO_TRADE("\\s*go\\s+to\\s+trade\\s*");
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
