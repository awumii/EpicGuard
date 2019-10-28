package pl.polskistevek.guard.bukkit.task;

import org.bukkit.Bukkit;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.listeners.AntiBotListener;
import pl.polskistevek.guard.bukkit.manager.Notificator;

public class ActionTask {
    private static int anim = 0;

    public static void start(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BukkitMain.getPlugin(BukkitMain.class), new Runnable() {
            @Override
            public void run() {
                if (AntiBotListener.cps == 0) {
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
                        anim = 0;
                    }
                }
            }
        }, 0L, 30L);
    }
}
