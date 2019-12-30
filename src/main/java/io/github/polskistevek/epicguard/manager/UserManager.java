package io.github.polskistevek.epicguard.manager;

import io.github.polskistevek.epicguard.object.User;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final Map<Player, User> userMap = new HashMap<>();

    public static User getUser(Player p) {
        return userMap.get(p);
    }

    public static void addUser(Player p) {
        User user = new User(p);
        userMap.put(p, user);
    }

    public static void removeUser(Player p) {
        userMap.remove(p);
    }
}
