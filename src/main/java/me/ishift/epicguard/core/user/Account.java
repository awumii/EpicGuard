package me.ishift.epicguard.core.user;

import me.ishift.epicguard.core.EpicGuard;

import java.util.List;

public class Account {
    private final List<String> nicknames;

    public Account(EpicGuard epicGuard, String address, String nickname) {
        this.nicknames = epicGuard.getStorageManager().getData().getStringList("account-limiter." + address);
        if (!this.nicknames.contains(nickname)) {
            this.nicknames.add(nickname);
        }
        epicGuard.getStorageManager().getData().set("account-limiter." + address, this.nicknames);
    }

    public List<String> getNicknames() {
        return this.nicknames;
    }
}
