package me.ishift.epicguard.core.handler;

import me.ishift.epicguard.core.EpicGuard;

import java.util.UUID;

public class DisconnectProvider {
    private final EpicGuard epicGuard;

    public DisconnectProvider(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public void handle(UUID uuid) {
        this.epicGuard.getUserManager().removeUser(uuid);
    }
}
