package model.generalenums;

public enum MessageEnum {
    PRIVATE_CHAT("pv"),
    PUBLIC_CHAT("pb"),
    ROOM("rm");
    final String type;
    MessageEnum(String type){
        this.type = type;
    }
}
