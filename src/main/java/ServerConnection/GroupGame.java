package ServerConnection;

import controller.MapController;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupGame implements Serializable {
    private int id;
    private int size;
    private User owner;
    private final ArrayList<User> players = new ArrayList<>();
    public final MapController mapController;
    public final boolean isPrivate;
    private ArrayList<String> chosenUsers = new ArrayList<>();
    public GroupGame(User owner, int size, MapController mapController) {
        this.owner = owner;
        this.size = size;
        addUser(owner);
        this.mapController = mapController;
        this.isPrivate = false;
    }

    public GroupGame(User owner, int size, MapController mapController, ArrayList<String> chosenUsers) {
        this.size = size;
        this.owner = owner;
        addUser(owner);
        this.mapController = mapController;
        this.chosenUsers = chosenUsers;
        isPrivate = true;
    }

    public synchronized void addUser(User user) {
        if (players.size() < size) players.add(user);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public User getOwner() {
        return owner;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public MapController getMapController() {
        return mapController;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Users:\n");
        if (isPrivate) {
            stringBuilder.append("(Specified)\n");
            for (int i = 0; i < chosenUsers.size(); i++) {
                stringBuilder.append((i+1)).append(". ").append(chosenUsers.get(i)).append("\n");
            }
        } else {
            for (int i = 0; i < players.size(); i++) {
                stringBuilder.append((i + 1)).append(". ").append(players.get(i).getUserName()).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public void removePlayer(String username) {
        User desiredUser = null;
        for (User player: players)
            if (player.getUserName().equals(username)) desiredUser = player;
        if (desiredUser != null && desiredUser.equals(owner) && players.size() > 1) owner = players.get(1);
        players.remove(desiredUser);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setChosenUsers(ArrayList<String> chosenUsers) {
        this.chosenUsers = chosenUsers;
    }

    public ArrayList<String> getChosenUsers() {
        return chosenUsers;
    }
}

