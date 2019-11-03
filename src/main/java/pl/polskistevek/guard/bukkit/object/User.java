package pl.polskistevek.guard.bukkit.object;

import org.bukkit.entity.Player;

import java.util.List;

public class User {
    private String code;
    private List<String> adresses;

    public User(Player player){
    }

    public List<String> getAdresses() {
        return adresses;
    }


    public void setAdresses(List<String> adresses) {
        this.adresses = adresses;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
