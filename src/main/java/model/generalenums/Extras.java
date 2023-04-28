package model.generalenums;

public enum Extras {
    SHRUB("shrub","a"),
    CHERRY_TREE("cherry tree","b"),
    OLIVE_TREE("olive tree","c"),
    COCONUT_TREE("coconut tree","d"),
    PALM_TREE("palm tree","d"),
    NORTH_ROCK("n","e"),
    SOUTH_ROCK("s","f"),
    WEST_ROCK("w","g"),
    EAST_ROCK("e","h");
    String name;
    String saveCode;
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