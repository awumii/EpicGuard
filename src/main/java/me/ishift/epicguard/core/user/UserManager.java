package me.ishift.epicguard.core.user;

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
        return this.userMap.get(uuid);
    }

    public void addUser(UUID uuid) {
        this.userMap.put(uuid, new User(uuid));
    }

    public void removeUser(UUID uuid) {
        this.userMap.remove(uuid);
    }
}
