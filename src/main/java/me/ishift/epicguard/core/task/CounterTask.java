package me.ishift.epicguard.core.task;

import me.ishift.epicguard.core.EpicGuard;

public class CounterTask implements Runnable {
    private final EpicGuard epicGuard;

    public CounterTask(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public void run() {
        this.epicGuard.getNotificationManager().notify(
                "&c&lEpicGuard &8» &7Connections: &c" + this.epicGuard.getConnectionPerSecond() +
                        "/s  &8░  &7Bot attack: " + (this.epicGuard.isAttack() ? "&4&l✖ Detected ✖" : "&2&l✔ Undetected ✔"));
        this.epicGuard.setConnectionPerSecond(0);
    }
}
