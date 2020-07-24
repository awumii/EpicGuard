package me.ishift.epicguard.core.handler;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.user.User;

import java.util.UUID;

public class ConnectionHandler {
    private final EpicGuard epicGuard;

    public ConnectionHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public void handle(UUID uuid, String address) {
        this.epicGuard.getUserManager().addUser(uuid);

        this.epicGuard.getMethodInterface().runTaskLater(() -> {
            User user = epicGuard.getUserManager().getUser(uuid);
            if (user != null) {
                epicGuard.getStorageManager().whitelist(address);
            }
        }, this.epicGuard.getConfig().autoWhitelistTime);
    }
}
