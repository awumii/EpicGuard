package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.gui.GuiMain;
import me.ishift.epicguard.bukkit.gui.GuiPlayers;
import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.util.misc.Notificator;
import me.ishift.epicguard.universal.Config;
import org.bukkit.Bukkit;

public class HeuristicsTask implements Runnable {
    private static int record = 0;
    private static int time = 0;
    private static int blacklistInc = 0;

    public static void setRecord(int i) {
        record = i;
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        HeuristicsTask.time = time;
    }

    public static int getBlacklistInc() {
        return blacklistInc;
    }

    public static void setBlacklistInc(int blacklistInc) {
        HeuristicsTask.blacklistInc = blacklistInc;
    }

    @Override
    public void run() {
        // Inventory Refreshing
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getOpenInventory().getTitle().equals("EpicGuard Management Menu")) {
                GuiMain.show(player);
            }
            if (player.getOpenInventory().getTitle().equals("EpicGuard Player Manager")) {
                GuiPlayers.show(player);
            }
        });

        if (AttackManager.isUnderAttack()) {
            time++;
        }

        if (!Config.heuristicsEnabled) {
            return;
        }

        final int diff = AttackManager.getConnectPerSecond() - record;
        if (AttackManager.getConnectPerSecond() > 0) {
            if (AttackManager.getConnectPerSecond() > record && diff > Config.heuristicsDiff) {
                record = AttackManager.getConnectPerSecond();
                if (!AttackManager.isUnderAttack()) {
                    AttackManager.setAttackMode(true);
                }
            }
        }
    }
}
