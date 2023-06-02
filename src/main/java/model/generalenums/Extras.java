package model.generalenums;

public enum Extras {
    SHRUB("shrub","a","shrub.jpeg"),
    CHERRY_TREE("cherry","b","shrub.jpeg"),
    OLIVE_TREE("olive","c","shrub.jpeg"),
    COCONUT_TREE("coconut","d","shrub.jpeg"),
    PALM_TREE("palm","d","shrub.jpeg"),
    NORTH_ROCK("n","e","shrub.jpeg"),
    SOUTH_ROCK("s","f","shrub.jpeg"),
    WEST_ROCK("w","g","shrub.jpeg"),
    EAST_ROCK("e","h","shrub.jpeg"),
    HOLD("hold","#","shrub.jpeg");
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