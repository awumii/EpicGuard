package me.ishift.epicguard.bukkit.util;

import com.comphenix.protocol.ProtocolLibrary;
import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.listener.TabCompletePacket;

public class MiscUtil {
    public static void registerProtocolLib(GuardBukkit plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new TabCompletePacket(plugin));
    }
}
