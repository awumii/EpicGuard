package me.ishift.epicguard.bukkit.manager;

import java.util.List;

public class User {
    private List<String> adresses;
    private boolean notifications;
    private String brand;
    private String ip;

    public User() {
        this.notifications = false;
        this.brand = "none";
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
        return adresses;
    }

    public void setAddresses(List<String> addresses) {
        this.adresses = addresses;
    }
}
