package io.github.polskistevek.epicguard.util;

import com.comphenix.protocol.ProtocolLibrary;
import io.github.polskistevek.epicguard.GuardBukkit;
import io.github.polskistevek.epicguard.listener.TabCompletePacket;

public class MiscUtil {
    public static void registerProtocolLib(GuardBukkit plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new TabCompletePacket(plugin));
    }
}
