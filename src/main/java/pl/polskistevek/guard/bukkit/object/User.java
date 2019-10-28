package pl.polskistevek.guard.bukkit.object;

import org.bukkit.entity.Player;

public class User {
    private String code;

    public User(Player player){
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
