package model;

import java.util.ArrayList;

public class Chat {
    private User admin;
    private final ArrayList<User> allUsers = new ArrayList<>();
    private final ArrayList<Message> allMessages = new ArrayList<>();

    public Chat(User admin) {
        this.admin = admin;
        allUsers.add(admin);

    }

}
