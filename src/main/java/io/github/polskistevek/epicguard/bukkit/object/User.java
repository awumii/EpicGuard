package io.github.polskistevek.epicguard.bukkit.object;

import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import org.bukkit.entity.Player;

import java.util.List;

public class User {
    private List<String> adresses;
    private boolean notifications;
    private String brand;

    public User(Player player) {
        this.notifications = DataFileManager.notificationUsers.contains(player.getName());
        this.brand = "none";
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
