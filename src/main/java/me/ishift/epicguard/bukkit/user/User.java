package me.ishift.epicguard.bukkit.user;

import org.bukkit.entity.Player;

import java.util.List;

public class User {
    private List<String> addressList;
    private boolean notifications;
    private String brand;
    private String address;

    public User(Player player) {
        this.notifications = false;
        this.brand = "none";
        this.address = player.getAddress().getAddress().getHostAddress();
    }

    public String getAddress() {
        return address;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public List<String> getAddresses() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }
}
