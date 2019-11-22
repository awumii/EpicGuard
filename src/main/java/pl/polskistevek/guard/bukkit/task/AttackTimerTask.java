package pl.polskistevek.guard.bukkit.task;

import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.listener.PreLoginListener;

public class AttackTimerTask implements Runnable {

    @Override
    public void run() {
        if (PreLoginListener.attack){
            if (PreLoginListener.cps < BukkitMain.CPS_ACTIVATE){
                PreLoginListener.attack = false;
            }
        }
    }
}
