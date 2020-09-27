package me.xneox.epicguard.core.manager;

public class AttackManager {
    private boolean attack;
    private int connectionPerSecond;
    private int totalBots;

    public boolean isAttack() {
        return this.attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public int getCPS() {
        return this.connectionPerSecond;
    }

    public void resetCPS() {
        this.connectionPerSecond = 0;
    }

    public void incrementCPS() {
        this.connectionPerSecond++;
        this.totalBots++;
    }

    public int getTotalBots() {
        return this.totalBots;
    }
}
