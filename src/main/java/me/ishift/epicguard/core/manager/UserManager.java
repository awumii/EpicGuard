package me.ishift.epicguard.core.manager;

import me.ishift.epicguard.core.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final Map<UUID, User> userMap = new HashMap<>();

    public Collection<User> getUsers() {
        return this.userMap.values();
    }

    public User getUser(UUID uuid) {
        User user = this.userMap.get(uuid);
        if (user == null) {
            return this.userMap.put(uuid, new User(uuid));
        }
        return user;
    }

    public void removeUser(UUID uuid) {
        this.userMap.remove(uuid);
    }
}
