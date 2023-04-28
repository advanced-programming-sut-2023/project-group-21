package view.message;

public enum TradeMenuMessage {
    NOT_FOUND_ID("this id does not found"),
    NOT_ENOUGH("not enough resource with this id"),
    NOT_ENOUGH_MONEY("you don't have enough money to buy resource"),
    NOT_EXISTENCE_IN_SELLER("you don't have this resource");



    private String message;
    TradeMenuMessage(String message){
        this.message = message;
    }
    public String toString(){
        return message;
    }
}
