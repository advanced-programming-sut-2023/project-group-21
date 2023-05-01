package view.message;

public enum TradeMenuMessage {
    NOT_FOUND_ID("this id does not found"),
    NOT_ENOUGH_AMOUNT("not enough resource with this id"),
    INVALID_RESOURCE_TYPE("the resource type is not valid"),
    NOT_ENOUGH_MONEY("you don't have enough money to buy resource"),
    INVALID_PRICE("the price is not non-negative"),
    INVALID_AMOUNT("the amount is not positive"),
    NOT_EXISTENCE_IN_SELLER("you don't have this resource"),
    SUCCESS("Done successfully"),
    INVALID_ID("The id is not valid"),
    CAN_NOT_AFFORD("You do not have enough money");



    private String message;
    TradeMenuMessage(String message){
        this.message = message;
    }
    public String toString(){
        return message;
    }
}
