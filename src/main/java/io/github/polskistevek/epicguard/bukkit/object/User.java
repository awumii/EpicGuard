package io.github.polskistevek.epicguard.bukkit.object;

import java.util.List;

public class User {
    private List<String> adresses;
    private int packetsPerSecond;

    public User() {
        this.packetsPerSecond = 0;
    }

    public void setPacketsPerSecond(int packetsPerSecond) {
        this.packetsPerSecond = packetsPerSecond;
    }

    public int getPacketsPerSecond() {
        return packetsPerSecond;
    }

    public List<String> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<String> adresses) {
        this.adresses = adresses;
    }
}
