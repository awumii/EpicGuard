package pl.polskistevek.guard.bukkit.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.gui.GuiMain;
import pl.polskistevek.guard.bukkit.listener.PreLoginListener;
import pl.polskistevek.guard.bukkit.manager.Notificator;

public class ActionTask implements Runnable {
    private static int anim = 0;

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.getOpenInventory().getTitle().equals("EpicGuard Menu")){
                GuiMain.show(p);
            }
        }
        if (PreLoginListener.cps == 0) {
            if (anim == 0) {
                Notificator.action(BukkitMain.ACTION_IDLE.replace("{ANIM}", "&8O&7oo"));
                anim = 1;
                return;
            }
            if (anim == 1) {
                Notificator.action(BukkitMain.ACTION_IDLE.replace("{ANIM}", "&7o&8O&7o"));
                anim = 2;
                return;
            }
            if (anim == 2) {
                Notificator.action(BukkitMain.ACTION_IDLE.replace("{ANIM}", "&7oo&8O"));
                anim = 3;
                return;
            }
            if (anim == 3) {
                Notificator.action(BukkitMain.ACTION_IDLE.replace("{ANIM}", "&7o&8O&7o"));
                anim = 0;
            }
        }
    }
}
