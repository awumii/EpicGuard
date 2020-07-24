package me.ishift.epicguard.core.handler;

import me.ishift.epicguard.core.EpicGuard;

public class PingHandler {
    private final EpicGuard epicGuard;

    public PingHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public void handle(String address) {
        this.epicGuard.getStorageManager().getPingCache().add(address);
    }
}
