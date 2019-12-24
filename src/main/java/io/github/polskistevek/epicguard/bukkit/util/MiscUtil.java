package io.github.polskistevek.epicguard.bukkit.util;

import com.comphenix.protocol.ProtocolLibrary;
import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.bukkit.listener.TabCompletePacket;

public class MiscUtil {
    public static void registerProtocolLib(GuardBukkit plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new TabCompletePacket(plugin));
    }
}
