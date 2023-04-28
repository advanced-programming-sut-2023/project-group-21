package view.message;

public enum MapMessages {
    NO_TREE("no such tree exists"),
    SUCCESS("success!"),
    INVALID_FORMAT("invalid format"),
    INVALID_DIRECTION("invalid direction"),
    NO_STONE("no stone!"),
    NO_TEXTURE("there is no texture with this format!"),
    INVALID_NUMBER("invalid number (X2 and y2 should be greater than x1 and y1)"),
    OUT_OF_INDEX("out of index");

    private String message;
    MapMessages(String message){
        this.message = message;
    }
}
