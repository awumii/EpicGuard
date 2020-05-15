package me.ishift.epicguard.common.task;

import lombok.AllArgsConstructor;
import me.ishift.epicguard.bukkit.util.BukkitNotify;
import me.ishift.epicguard.bungee.util.BungeeNotify;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.types.Platform;

@AllArgsConstructor
public class MonitorTask implements Runnable {
    private final AttackManager manager;
    private final Platform platform;

    @Override
    public void run() {
        final String message = Messages.monitorActionAttack
                .replace("{CPS}", String.valueOf(this.manager.getConnectPerSecond()))
                .replace("{BLOCKED}", String.valueOf(this.manager.getTotalBots()))
                .replace("{STATUS}", this.manager.isAttackMode() ? "&a✔" : "&c✖");
        if (this.platform == Platform.BUKKIT) {
            BukkitNotify.notify(message);
        } else {
            BungeeNotify.notify(message);
        }
    }
}
