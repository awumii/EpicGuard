package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBuildListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (EpicGuardBukkit.getInstance().getOperatorProtection().execute(event.getPlayer(), "", new String[0])) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (EpicGuardBukkit.getInstance().getOperatorProtection().execute(event.getPlayer(), "", new String[0])) {
            event.setCancelled(true);
        }
    }
}
