package me.ishift.epicguard.bukkit.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.Config;

public class TabCompletePacket extends PacketAdapter {
    public TabCompletePacket(final GuardBukkit plugin) {
        super(plugin, PacketType.Play.Client.TAB_COMPLETE);
    }

    public void onPacketReceiving(final PacketEvent event) {
        if (Config.tabCompleteBlock) {
            event.setCancelled(true);
        }
    }
}