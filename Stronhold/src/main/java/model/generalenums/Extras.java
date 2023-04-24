package model.generalenums;

public enum Extras {
    SHRUB("shrub"),
    CHERRY_TREE("cherry tree"),
    OLIVE_TREE("olive tree"),
    COCONUT_TREE("coconut tree"),
    PALM_TREE("palm tree"),
    NORTH_ROCK("n"),
    SOUTH_ROCK("s"),
    WEST_ROCK("w"),
    EAST_ROCK("e");
    String name;
    Extras(String name) {
        this.name = name;
    }

    public static Extras getExtrasByName(String name) {
        for (Extras extras: Extras.values()) {
            if (extras.name.equals(name)) return extras;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
