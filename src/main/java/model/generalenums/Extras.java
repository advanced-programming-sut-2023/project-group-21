package model.generalenums;

public enum Extras {
    SHRUB("shrub","a","shrub.png"),
    CHERRY_TREE("cherry","b","cherry.png"),
    OLIVE_TREE("olive","c","olive.png"),
    COCONUT_TREE("coconut","d","coconut.png"),
    PALM_TREE("palm","d","palm.png"),
    NORTH_ROCK("n","e","shrub.jpeg"),
    SOUTH_ROCK("s","f","shrub.jpeg"),
    WEST_ROCK("w","g","shrub.jpeg"),
    EAST_ROCK("e","h","shrub.jpeg"),
    HOLD("hold","#","hold.png");
    private final String name;
    private final String saveCode;
    private final String imagePath;
    Extras(String name,String saveCode,String imagePath) {
        this.name = name;
        this.saveCode = saveCode;
        this.imagePath = imagePath;
    }

    public static Extras getExtrasByName(String name) {
        for (Extras extras: Extras.values()) {
            if (extras.name.equals(name)) return extras;
        }
        return null;
    }

    public static Extras getExtrasByCode(String saveCode) {
        for (Extras extras: Extras.values()) {
            if (extras.saveCode.equals(saveCode)) return extras;
        }
        return null;
    }

    public String getImagePath(){
        return imagePath;//new option!
    }

    public String getName() {
        return name;
    }

    public String getSaveCode() {
        return saveCode;
    }
}