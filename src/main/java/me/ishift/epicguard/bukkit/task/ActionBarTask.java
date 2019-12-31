package me.ishift.epicguard.bukkit.task;

import me.ishift.epicguard.bukkit.gui.GuiMain;
import me.ishift.epicguard.bukkit.gui.GuiPlayers;
import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.util.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.Notificator;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionBarTask implements Runnable {
    private static int animation = 0;

    @Override
    public void run() {
        try {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getOpenInventory().getTitle().equals("EpicGuard Management Menu")) {
                    GuiMain.show(p);
                }
                if (p.getOpenInventory().getTitle().equals("EpicGuard Player Manager")) {
                    GuiPlayers.show(p);
                }
            }
            if (AttackManager.getConnectPerSecond() == 0) {
                if (animation == 0) {
                    Notificator.action(MessagesBukkit.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&8O&7oo").replace("{CPS}", String.valueOf(AttackManager.getConnectPerSecond())));
                    animation = 1;
                    return;
                }
                if (animation == 1) {
                    Notificator.action(MessagesBukkit.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&7o&8O&7o").replace("{CPS}", String.valueOf(AttackManager.getConnectPerSecond())));
                    animation = 2;
                    return;
                }
                if (animation == 2) {
                    Notificator.action(MessagesBukkit.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&7oo&8O").replace("{CPS}", String.valueOf(AttackManager.getConnectPerSecond())));
                    animation = 3;
                    return;
                }
                if (animation == 3) {
                    Notificator.action(MessagesBukkit.ACTIONBAR_NO_ATTACK.replace("{ANIM}", "&7o&8O&7o").replace("{CPS}", String.valueOf(AttackManager.getConnectPerSecond())));
                    animation = 0;
                }
            }
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}
