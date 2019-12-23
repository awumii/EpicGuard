package io.github.polskistevek.epicguard.bukkit.object;

import java.util.List;

public class User {
    private List<String> adresses;

    public User() {
    }

    public List<String> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<String> adresses) {
        this.adresses = adresses;
    }
}
