package view.message;

public enum GameMessage {
    INVALID_COMMAND("invalid command!"),
    NO_RESOURCE("no resource!"),
    NO_SHOP("you can't access shop menu!"),
    UNABLE_TO_DROP_BUILDING1("you are unable to drop building because ..."),
    UNABLE_TO_DROP_BUILDING2("you are unable to drop building because ...."),
    UNABLE_TO_DROP_BUILDING3("you are unable to drop building because ....."),
    OUT_OF_RANGE("out of range!"),
    NO_PEOPLE_TO_SELECT("this cell is empty"),
    SUCCESS("success!"),
    NO_SELECTED_UNIT("you didn't choose any unit yet!"),
    UNREACHABLE_CELL("this cell is unreachable"),
    UNREACHABLE_DISTANCE("this distance is unreachable"),
    INVALID_STATE("invalid state"),
    NO_ENEMY_TO_FIGHT("there is no enemy to fight!"),
    NO_SUITABLE("no suitable troop for this purpose"),
    INVALID_FEAR_RATE("invalid food rate!"),
    NO_BUILDING("there is no building with this name!"),
    INVALID_FOOD_RATE("invalid food rate!"),
    ALREADY_BUILDING("already there is a building"),
    FAILURE1("failure due to ...1"),
    FAILURE2("sorry unable to dig tunnel!"),
    FAILURE3("failure due to ...3"),
    FAILURE4("failure due to ...4"),
    FAILURE5("failure due to ...5"),
    NOT_ENOUGH_RESOURCE("not enough resource!"),
    NO_BUILDING_TO_SELECT("there is no building to select!"),
    NO_SELECTED_BUILDING("no selected building!"),
    ANOTHER_PURPOSE("the selected unit has another unit!"),
    SELECT_TUNNELER("please first select tunneler!"),
    INVALID_DIRECTION("invalid direction!"),
    NO_OIL("the worker does not have oil!"),
    REPAIR("no need to repair the building"),
    FACTORS("repair me!"),
    UNABLE_TO_MOVE("unable to move!"),
    NO_SUITABLE_BUILDING("the building is not suitable for this purpose"),
    INVALID_RESOURCE_NAME("Storage name is invalid");
    private final String message;
    GameMessage(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }

}
