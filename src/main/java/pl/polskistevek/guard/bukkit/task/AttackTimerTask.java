package pl.polskistevek.guard.bukkit.task;

import pl.polskistevek.guard.bukkit.manager.AttackManager;

public class AttackTimerTask implements Runnable {

    @Override
    public void run() {
        AttackManager.attackMode = false;
    }
}
