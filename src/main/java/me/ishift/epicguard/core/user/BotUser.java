package me.ishift.epicguard.core.user;

import de.leonhard.storage.Json;

import java.util.List;

public class BotUser {
    private final String address;
    private final String nickname;

    public BotUser(String address, String nickname) {
        this.address = address;
        this.nickname = nickname;
    }

    public String getAddress() {
        return this.address;
    }

    public String getNickname() {
        return this.nickname;
    }

    public List<String> getNicknames() {
        String ip = address.replace(".", "@");
        Json data = new Json("storage", "plugins/EpicGuard/data");
        List<String> nicknames = data.getStringList("account-limiter." + ip);

        if (!nicknames.contains(this.nickname)) {
            nicknames.add(this.nickname);
        }
        data.set("account-limiter." + ip, nicknames);
        return nicknames;
    }
}
