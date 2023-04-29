package view.message;

public enum ShopMessage {
    NOT_A_RESOURCE("spelling mistake! not a resource name!"),
    NOT_YOUR_RESOURCE("you don't have the resource!"),
    NOT_ENOUGH("your inventory is not sufficient"),
    NOT_ENOUGH_MONEY("you don't have enough money to buy"),
    NO_CAPACITY("you don't have capacity to store them!"),
    INVALID_NUMBER("invalid number!");
    private final String message;
    ShopMessage(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
