package me.ishift.epicguard.core.task;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.util.ChatUtils;
import org.bukkit.Bukkit;

public class CounterTask implements Runnable {
    private final EpicGuard epicGuard;

    public CounterTask(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage(ChatUtils.colored("&8[&aEpicGuard&8] &7Connections: &c" + this.epicGuard.getConnectionPerSecond() + "/s&7, ATK: &c" + this.epicGuard.isAttack()));
        if (this.epicGuard.getConnectionPerSecond() > this.epicGuard.getConfig().maxCps) {
            this.epicGuard.setAttack(true);
        }
        this.epicGuard.setConnectionPerSecond(0);
    }
}
