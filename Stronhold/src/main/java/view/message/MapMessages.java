package view.message;

public enum MapMessages {
    NO_TREE("no such tree exists"),
    SUCCESS("success!"),
    INVALID_FORMAT("invalid format"),
    INVALID_DIRECTION("invalid direction"),
    NO_STONE("no stone!"),
    NO_TEXTURE("there is no texture with this format!"),
    INVALID_NUMBER("invalid number (X2 and y2 should be greater than x1 and y1)"),
    OUT_OF_INDEX("out of index"),
    UNABLE_TO_PUT_HOLD("unable to put hold!"),
    TOO_MANY_HOLD("there is too many hold in the map!")
    ;

    private final String message;
    MapMessages(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
