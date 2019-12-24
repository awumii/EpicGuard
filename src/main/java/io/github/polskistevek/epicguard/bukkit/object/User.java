package io.github.polskistevek.epicguard.bukkit.object;

import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import org.bukkit.entity.Player;

import java.util.List;

public class User {
    private List<String> adresses;
    private boolean notifications;

    public User(Player player) {
        this.notifications = DataFileManager.notificationUsers.contains(player.getName());
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public List<String> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<String> adresses) {
        this.adresses = adresses;
    }
}
