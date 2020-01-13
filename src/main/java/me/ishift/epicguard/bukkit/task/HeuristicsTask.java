package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.util.Notificator;
import me.ishift.epicguard.universal.Config;

public class HeuristicsTask implements Runnable {
    private static int record = 0;

    @Override
    public void run() {
        Notificator.action("&8--=[ &7Listening for attack... &8]=--");
        final int diff = AttackManager.getConnectPerSecond() - record;

        if (AttackManager.getConnectPerSecond() > 0) {
            final int percent = record * 100 / AttackManager.getConnectPerSecond();

            if (AttackManager.getConnectPerSecond() > record && diff > Config.CONNECT_SPEED) {
                Notificator.broadcast("&7DETECTED: &e" + record + " &7-> &e" + AttackManager.getConnectPerSecond() + " &7(&a" + percent + "%&7) bots per second!");
                record = AttackManager.getConnectPerSecond();
                AttackManager.setAttackMode(true);
            }
        }
    }

    public static void setRecord(int i) {
        record = i;
    }
}
