package model.generalenums;

public enum GroundTexture {
    SOIL("soil", ColorCode.WHITE),
    GRAVEL("gravel", ColorCode.RED),
    STONE("stone", ColorCode.INTENSE_BLACK),
    ROCK("rock", ColorCode.INTENSE_WHITE),
    IRON("iron", ColorCode.INTENSE_RED),
    GRASS("grass", ColorCode.INTENSE_GREEN),
    MEADOW("meadow", ColorCode.YELLOW),
    DENSE_MEADOW("dense meadow", ColorCode.INTENSE_YELLOW),
    PETROL("petrol", ColorCode.BLACK),
    PLAIN("plain", ColorCode.INTENSE_WHITE),
    SHALLOW_WATER("shallow water", ColorCode.CYAN),
    RIVER("river", ColorCode.BLUE),
    SMALL_LAKE("small lake", ColorCode.PURPLE),
    BIG_LAKE("big lake", ColorCode.INTENSE_PURPLE),
    BEACH("beach", ColorCode.GREEN),
    SEA("sea", ColorCode.INTENSE_BLUE);

    public enum ColorCode {
        WHITE("\033[47m"),
        INTENSE_WHITE("\033[0;107m"),
        CYAN("\033[46m"),
        INTENSE_CYAN("\033[0;106m"),
        PURPLE("\033[45m"),
        INTENSE_PURPLE("\033[0;105m"),
        BLUE("\033[44m"),
        INTENSE_BLUE("\033[0;104m"),
        YELLOW("\033[43m"),
        INTENSE_YELLOW("\033[0;103m"),
        GREEN("\033[42m"),
        INTENSE_GREEN("\033[0;102m"),
        RED("\033[41m"),
        INTENSE_RED("\033[0;101m"),
        BLACK("\033[40m"),
        INTENSE_BLACK("\033[0;100m");

        String code;
        ColorCode(String code) {
            this.code = code;
        }
    }
    String name;
    ColorCode colorCode;

    GroundTexture(String name, ColorCode colorCode) {
        this.name = name;
        this.colorCode = colorCode;
    }

    public static GroundTexture getTextureByName(String name) {
        for (GroundTexture groundTexture: GroundTexture.values()) {
            if (groundTexture.name.equals(name)) return groundTexture;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return colorCode.code;
    }
}
