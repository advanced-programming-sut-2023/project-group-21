package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private static ArrayList<User> users = new ArrayList<>();
    public static final ArrayList<String> SLOGANS = new ArrayList<>(Arrays.asList(""));

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
