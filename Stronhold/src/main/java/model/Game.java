package model;

import model.generalenums.GroundTexture;

import java.util.*;

public class Game {
    private static ArrayList<User> users = new ArrayList<>();
    public static final Map<Integer, Double> foodConsumption = Map.of(-2, (double) 0, -1, 0.5, 0, (double) 1, 1, 1.5, 2, (double) 2);
    public static final ArrayList<String> SLOGANS = new ArrayList<>(Arrays.asList(""));
    public static final String[] SECURITY_QUESTION = { "what is your favorite food ?", "what is your father s name ?",
            "what is your oldest sibling's first name?" };
    public static final List<GroundTexture> UNPASSABLE = Arrays.asList(GroundTexture.ROCK, GroundTexture.RIVER,
            GroundTexture.SMALL_LAKE, GroundTexture.BIG_LAKE, GroundTexture.SEA);
    public static enum TaxDetails {
        A(-3, -1, 7),
        B(-2, -0.8, 5),
        C(-1, -0.6, 3),
        D(0, 0, 1),
        E(1, 0.6, -2),
        F(2, 0.8, -4),
        G(3, 1, -6),
        H(4, 1.2, -8),
        I(5, 1.4, -12),
        J(6, 1.6, -16),
        K(7, 1.8, -20),
        L(8, 2, -24);
        int number, popularityRate;
        double tax;
        TaxDetails(int number, double tax, int popularityRate) {
            this.number = number;
            this.tax = tax;
            this.popularityRate = popularityRate;
        }

        public static double getTax(int number) {
            for (TaxDetails taxDetail: TaxDetails.values()) if (taxDetail.number == number) return taxDetail.tax;
            return 20;
        }

        public static int getPopularity(int number) {
            for (TaxDetails taxDetail: TaxDetails.values()) if (taxDetail.number == number) return taxDetail.popularityRate;
            return 20;
        }
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getUserByUsername(String username) {
        for (User user: users) if (user.getUserName().equals(username)) return user;
        return null;
    }
}
