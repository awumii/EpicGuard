package pl.polskistevek.guard.bukkit.task;

import org.bukkit.Bukkit;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.listener.PreLoginListener;

public class AttackTask {
    public static void start(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BukkitMain.getPlugin(BukkitMain.class), () -> {
            if (PreLoginListener.attack){
                if (PreLoginListener.cps < BukkitMain.CPS_ACTIVATE){
                    PreLoginListener.attack = false;
                }
            }
        }, 0L, 260L);
    }
}
