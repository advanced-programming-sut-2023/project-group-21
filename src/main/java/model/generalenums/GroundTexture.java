package model.generalenums;

public enum GroundTexture {
    SOIL("soil", ColorCode.WHITE,"i"),
    GRAVEL("gravel", ColorCode.RED,"j"),
    STONE("stone", ColorCode.INTENSE_BLACK,"k"),
    ROCK("rock", ColorCode.INTENSE_WHITE,"l"),
    IRON("iron", ColorCode.INTENSE_RED,"m"),
    GRASS("grass", ColorCode.INTENSE_GREEN,"n"),
    MEADOW("meadow", ColorCode.YELLOW,"o"),
    DENSE_MEADOW("dense meadow", ColorCode.INTENSE_YELLOW,"p"),
    PETROL("petrol", ColorCode.BLACK,"q"),
    PLAIN("plain", ColorCode.INTENSE_WHITE,"r"),
    SHALLOW_WATER("shallow water", ColorCode.CYAN,"s"),
    RIVER("river", ColorCode.BLUE,"t"),
    SMALL_LAKE("small lake", ColorCode.PURPLE,"u"),
    BIG_LAKE("big lake", ColorCode.INTENSE_PURPLE,"x"),
    BEACH("beach", ColorCode.GREEN,"y"),
    SEA("sea", ColorCode.INTENSE_BLUE,"z");

    public enum ColorCode {
        WHITE("\033[47m","#ffffff"),
        INTENSE_WHITE("\033[0;107m","#aaaaaa"),
        CYAN("\033[46m","#0D98BA"),
        INTENSE_CYAN("\033[0;106m","#5D98BA"),
        PURPLE("\033[45m","#a020f0"),
        INTENSE_PURPLE("\033[0;105m","#d020f0"),
        BLUE("\033[44m","#0000ff"),
        INTENSE_BLUE("\033[0;104m","#2200ff"),
        YELLOW("\033[43m","#ffff00"),
        INTENSE_YELLOW("\033[0;103m","#ffbb00"),
        GREEN("\033[42m","#00ff00"),
        INTENSE_GREEN("\033[0;102m0","#11cc00"),
        RED("\033[41m","#ff1111"),
        INTENSE_RED("\033[0;101m","#dd1122"),
        BLACK("\033[40m","#000000"),
        INTENSE_BLACK("\033[0;100m","#333333");

        String code;
        String RGBCode;
        ColorCode(String code,String RGBCode) {
            this.code = code;
            this.RGBCode = RGBCode;
        }
        public String getRGBCode(){
            return RGBCode;
        }
    }
    String name;
    ColorCode colorCode;
    String saveCode;

    GroundTexture(String name, ColorCode colorCode,String saveCode) {
        this.name = name;
        this.colorCode = colorCode;
        this.saveCode = saveCode;
    }

    public static GroundTexture getTextureByName(String name) {
        for (GroundTexture groundTexture: GroundTexture.values()) {
            if (groundTexture.name.equals(name)) return groundTexture;
        }
        return null;
    }

    public static GroundTexture getTextureBySaveCode(String saveCode) {
        for (GroundTexture groundTexture: GroundTexture.values()) {
            if (groundTexture.saveCode.equals(saveCode)) return groundTexture;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return colorCode.code;
    }

    public String getSaveCode(){
        return this.saveCode;
    }
    public String getRGB(){
        return colorCode.RGBCode;
    }
}