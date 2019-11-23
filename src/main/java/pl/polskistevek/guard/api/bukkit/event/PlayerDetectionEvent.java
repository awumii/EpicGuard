package pl.polskistevek.guard.api.bukkit.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.polskistevek.guard.utils.KickReason;

public class PlayerDetectionEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private String player;
    private KickReason reason;
    private boolean isCancelled;

    public PlayerDetectionEvent(String player, KickReason reason){
        this.player = player;
        this.reason = reason;
        this.isCancelled = false;
    }

    public String getPlayer() {
        return player;
    }

    public KickReason getReason() {
        return reason;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
