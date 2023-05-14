package model.generalenums;

public enum Extras {
    SHRUB("shrub","a"),
    CHERRY_TREE("cherry","b"),
    OLIVE_TREE("olive","c"),
    COCONUT_TREE("coconut","d"),
    PALM_TREE("palm","d"),
    NORTH_ROCK("n","e"),
    SOUTH_ROCK("s","f"),
    WEST_ROCK("w","g"),
    EAST_ROCK("e","h"),
    HOLD("hold","#");
    private final String name;
    private final String saveCode;
    Extras(String name,String saveCode) {
        this.name = name;
        this.saveCode = saveCode;
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

    public String getName() {
        return name;
    }

    public String getSaveCode() {
        return saveCode;
    }
}